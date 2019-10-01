package com.amazonaws.ssmcachingclient;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathRequest;
import software.amazon.awssdk.services.ssm.model.Parameter;
import software.amazon.awssdk.services.ssm.model.ParameterNotFoundException;
import software.amazon.awssdk.services.ssm.model.ParameterType;
import software.amazon.awssdk.services.ssm.paginators.GetParametersByPathIterable;

/**
 * The SsmParameterCachingClient is a thread-safe class that provides an in-memory caching layer over SSM Parameter Store.
 * This is useful when using the "standard" SSM Parameter Store (free service), which only supports a small number of
 * transactions per second (TPS).
 * <p>
 * Security Note: All calls made to SSM Parameter Store decrypt SecureString parameter values and cache them in plain text in the
 * in-memory cache. However, this class does not write decrypted SecureString values to disk.
 * <p>
 * <h3>Using pathPrefix</h3>
 * <p>
 * The client accepts a parameter path prefix, which allows it to make more efficient bulk calls to the SSM Parameter Store API
 * and bulk load all parameters under the given path prefix at once. For example, you might use the following hierarchy for storing
 * parameters for FooService:
 * <p>
 * <pre>
 *     /FooService/Dev/DbTableName
 *     /FooService/Dev/NotificationTopicArn
 *     /FooService/Prod/DbTableName
 *     /FooService/Prod/NotificationTopicArn
 * </pre>
 * <p>
 * In this example, there are separate parameters for FooService depending on if the environment is Dev or Prod. If SsmParameterCachingClient
 * is passed "/FooService/Dev/" as the <code>pathPrefix</code>, it will make a bulk call to the SSM Parameter Store API to load and cache all
 * parameters that start with "/FooService/Dev/".
 * <p>
 * Also, the get methods on the client will automatically prepend the pathPrefix so you can initialize the client once with the pathPrefix and
 * then reference the parameters without the prefix from there on out:
 * <p>
 * <pre>
 * // initialization code
 * SsmClient ssm = SsmClient.create();
 * SsmParameterCachingClient client = new SsmParameterCachingClient(ssm, Duration.ofSeconds(60), "/FooService/Dev/");
 *
 * ...
 *
 * // elsewhere in your business logic
 * client.getAsString("DbTableName");
 * </pre>
 * <p>
 * Using this pattern, only your initialization code needs to know what environment you're running in, and the rest of your business logic can
 * refer to the parameters without the prefix.
 * <p>
 * For more information on organizing parameters into hierarchies, see the
 * <a href="https://docs.aws.amazon.com/systems-manager/latest/userguide/sysman-paramstore-su-organize.html">AWS Systems Manager User Guide</a>.
 */
@Slf4j
public class SsmParameterCachingClient {
    private final SsmClient ssm;
    private final Duration ttl;
    private final String pathPrefix;
    private final boolean allowStaleValues;
    private final boolean bulkLoad;
    private final Clock clock;

    private volatile ConcurrentMap<String, CachedParameter> cache;

    @Value
    private static final class CachedParameter {
        private final Parameter parameter;
        private final Instant loadedAt;
    }

    /**
     * Package-private constructor for unit testing only.
     *
     * @param ssm              AWS SSM client.
     * @param ttl              The cache time-to-live (TTL), which is the duration before a cached value will be considered stale.
     * @param pathPrefix       Parameter path prefix to prepend to all parameter names passed to get methods.
     * @param allowStaleValues If set to <code>true</code>, the client will fall back to returning stale cached values if SSM cannot be reached to refresh the cache.
     * @param bulkLoad         If set to <code>true</code> and pathPrefix is non-null, the client will bulk load and cache all parameters for the given pathPrefix once the cache is stale, rather than loading them one at a time.
     * @param clock            Clock override for unit testing.
     */
    SsmParameterCachingClient(@NonNull final SsmClient ssm, final Duration ttl, final String pathPrefix, final boolean allowStaleValues, final boolean bulkLoad, final Clock clock) {
        this.ssm = ssm;
        this.ttl = ttl;
        this.pathPrefix = pathPrefix;
        this.allowStaleValues = allowStaleValues;
        this.bulkLoad = bulkLoad;
        this.clock = clock;

        this.cache = new ConcurrentHashMap<>();
    }

    /**
     * Create a new caching client.
     *
     * @param ssm              AWS SSM client.
     * @param ttl              The cache time-to-live (TTL), which is the duration before a cached value will be considered stale.
     * @param pathPrefix       Parameter path prefix to prepend to all parameter names passed to get methods.
     * @param allowStaleValues If set to <code>true</code>, the client will fall back to returning stale cached values if SSM cannot be reached to refresh the cache.
     * @param bulkLoad         If set to <code>true</code> and pathPrefix is non-null, the client will bulk load and cache all parameters for the given pathPrefix once the cache is stale, rather than loading them one at a time.
     */
    public SsmParameterCachingClient(final SsmClient ssm, final Duration ttl, final String pathPrefix, final boolean allowStaleValues, final boolean bulkLoad) {
        this(ssm, ttl, pathPrefix, allowStaleValues, bulkLoad, Clock.systemUTC());
    }

    /**
     * Convenience constructor for creating a new caching client. Defaults to setting <code>allowStaleValues</code> and <code>bulkLoad</code> to <code>true</code>.
     *
     * @param ssm        AWS SSM client.
     * @param ttl        The cache time-to-live (TTL), which is the duration before a cached value will be considered stale.
     * @param pathPrefix Parameter path prefix to prepend to all parameter names passed to #get().
     */
    public SsmParameterCachingClient(final SsmClient ssm, final Duration ttl, final String pathPrefix) {
        this(ssm, ttl, pathPrefix, true, true);
    }

    /**
     * Convenience constructor for creating a new caching client. Defaults to setting <code>pathPrefix</code> to <code>null</code>, and <code>allowStaleValues</code> and <code>bulkLoad</code> to <code>true</code>.
     *
     * @param ssm AWS SSM client.
     * @param ttl The cache time-to-live (TTL), which is the duration before a cached value will be considered stale.
     */
    public SsmParameterCachingClient(final SsmClient ssm, final Duration ttl) {
        this(ssm, ttl, null);
    }

    /**
     * Get a parameter value.
     *
     * @param name Parameter name to get. If a pathPrefix has been supplied, it is prepended to name.
     * @return Full Parameter object returned by SSM client.
     */
    public Parameter get(final String name) {
        CachedParameter cached = getCachedParameter(name);
        if (isStale(cached)) {
            log.debug("SSM Parameter cache miss for pathPrefix={}, name={}. Attempting to load value from SSM.", pathPrefix, name);
            try {
                cached = load(name);
            } catch (Exception e) {
                // only case where we don't allow stale values is if we found out the parameter they're requesting doesn't exist
                if (e instanceof ParameterNotFoundException) {
                    throw e;
                }

                if (allowStaleValues && cached != null) {
                    log.warn(String.format("Failed to update cache from SSM for pathPrefix=%s, name=%s. Returning stale value instead.", pathPrefix, name), e);
                    return cached.getParameter();
                }
                throw e;
            }
        } else {
            log.debug("SSM Parameter cache hit for pathPrefix={}, name={}. Returning cached value.", pathPrefix, name);
        }

        return cached.parameter;
    }

    private CachedParameter getCachedParameter(final String name) {
        String resolvedName = resolvedName(name);
        return cache.get(resolvedName);
    }

    private String resolvedName(final String name) {
        if (pathPrefix == null) {
            return name;
        }
        return pathPrefix + name;
    }

    private boolean isStale(CachedParameter cached) {
        return cached == null || Instant.now(clock).isAfter(cached.getLoadedAt().plus(ttl));
    }

    private CachedParameter load(String name) {
        String resolvedName = resolvedName(name);
        if (bulkLoad && pathPrefix != null) {
            return bulkLoad(resolvedName);
        }
        return loadSingleValue(resolvedName);
    }

    private CachedParameter bulkLoad(final String resolvedName) {
        log.debug("Bulk loading and caching all parameters for pathPrefix={} from SSM", pathPrefix);
        // Call GetParametersByPath API, paginate through and replace cache with new values.
        GetParametersByPathRequest request = GetParametersByPathRequest.builder()
                .path(pathPrefix)
                .recursive(true)
                .withDecryption(true)
                .build();
        GetParametersByPathIterable iterable = ssm.getParametersByPathPaginator(request);

        ConcurrentMap<String, CachedParameter> newCache = new ConcurrentHashMap<>();
        iterable.stream()
                .flatMap(r -> r.parameters().stream())
                .forEach(p -> newCache.put(p.name(), new CachedParameter(p, Instant.now(clock))));

        if (!newCache.containsKey(resolvedName)) {
            // if parameter they requested isn't in the results, it doesn't exist in SSM. Call SSM for that specific
            // parameter so the SSM not found exception is thrown back to the caller.
            log.debug("Bulk load results did not contain parameter {}", resolvedName);
            loadSingleValue(resolvedName);

            // shouldn't get here
            throw new IllegalStateException(
                    String.format("Could not find SSM parameter via GetParametersByPath(path=%s), but found it with GetParameter(name=%s). This is probably a bug in the caching client",
                            pathPrefix, resolvedName));
        }

        // replace cache in case previously cached values have since been removed from SSM parameter store
        cache = newCache;
        return cache.get(resolvedName);
    }

    private CachedParameter loadSingleValue(final String resolvedName) {
        log.debug("Loading and caching single value {} from SSM", resolvedName);
        GetParameterRequest request = GetParameterRequest.builder()
                .name(resolvedName)
                .withDecryption(true)
                .build();

        GetParameterResponse response = ssm.getParameter(request);

        CachedParameter cached = new CachedParameter(response.parameter(), Instant.now(clock));
        cache.put(response.parameter().name(), cached);

        return cached;
    }

    /**
     * Get a parameter value as a String.
     *
     * @param name Parameter name to get. If a pathPrefix has been supplied, it is prepended to name.
     * @return Parameter value as a string.
     */
    public String getAsString(final String name) {
        return get(name).value();
    }

    /**
     * Get a parameter value as a List of Strings.
     *
     * @param name Parameter name to get. If a pathPrefix has been supplied, it is prepended to name.
     * @return Parameter value as a list of strings. List is generated by splitting the parameter string using ',' as a delimiter.
     * @throws IllegalArgumentException if returned parameter is not of type StringList.
     */
    public List<String> getAsStringList(final String name) {
        Parameter parameter = get(name);
        if (parameter.type() != ParameterType.STRING_LIST) {
            throw new IllegalArgumentException(String.format("Parameter %s is of type %s, expected StringList", parameter.name(), parameter.type()));
        }

        return Arrays.asList(parameter.value().split(","));
    }
}

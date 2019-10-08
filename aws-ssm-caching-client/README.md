# aws-ssm-java-caching-client

aws-ssm-java-caching-client is a Java client-side caching library for AWS Systems Manager Parameter Store. Note, this client wraps the Java SDK V2 version of the AWS SSM client.

## Using the Client

To use the client, first add a dependency on the library.

Maven:

```xml
<dependency>
  <groupId>software.amazon.serverless</groupId>
  <artifactId>aws-ssm-java-caching-client</artifactId>
  <version>1.0.0</version>
</dependency>
```

Gradle:

```gradle
dependencies {
  compile 'software.amazon.serverless:aws-ssm-java-caching-client:1.0.0'
}
```

### Basic Usage

The following example creates a basic caching client that will load and cache individual SSM Parameters on demand. In this example, the time-to-live of the cached Parameter values is set to 1 minute.

```java
SsmClient ssm = SsmClient.create();

// cache parameter values for 1 minute before reloading them from SSM
SsmParameterCachingClient client = new SsmParameterCachingClient(ssm, Duration.ofMinutes(1));

// simple string parameter
String stringParameter = client.getAsString("string_param");
// SecureString parameters are decrypted on load
String secureStringParameter = client.getAsString("secure_string_param");
// comma-delimited StringList parameters
List<String> stringListParameter = client.getAsStringList("string_list_param");

// can also get the full Parameter object returned by the SSM API
Parameter parameter = client.get("string_param");
System.out.println("Full Parameter metadata: " + parameter);
```

### Parameter Hierarchy Support

The `SsmParameterCachingClient` constructor also accepts a parameter path prefix, which takes advantage of SSM Parameter Store's parameter hierarchy support to make more efficient bulk calls to load all parameters under the given path prefix at once. For example, you might use the following hierarchy for storing parameters for FooService:

```text
  /FooService/Dev/DbTableName
  /FooService/Dev/NotificationTopicArn
  /FooService/Prod/DbTableName
  /FooService/Prod/NotificationTopicArn
```

In this example, there are separate parameters for FooService depending on if the environment is Dev or Prod. If `SsmParameterCachingClient` is passed `/FooService/Dev/` as the `pathPrefix`, it will make a bulk call to the SSM Parameter Store API to load and cache all parameters that start with `/FooService/Dev/`.

Also, the get methods on the client will automatically prepend the path prefix so you can initialize the client once with the path prefix and then reference the parameters without the prefix from there on out:

```java
// initialization code, e.g., Spring/Guice config
SsmClient ssm = SsmClient.create();
SsmParameterCachingClient client = new SsmParameterCachingClient(ssm, Duration.ofMinutes(1), "/FooService/Dev/");

...

// elsewhere in your business logic

// client automatically prepends prefixPath so this resolves to /FooService/Dev/DbTableName
// on cache miss, the client bulk loads/caches all parameters under /FooService/Dev/
client.getAsString("DbTableName");
```

The advantage of using this pattern is that only your initialization code needs to know what environment it's running in, and the rest of your business logic can refer to the parameters without the prefix.

For more information on organizing parameters into hierarchies, see the [AWS Systems Manager User Guide](https://docs.aws.amazon.com/systems-manager/latest/userguide/sysman-paramstore-su-organize.html)

### Production Considerations

This section discusses the features included in this caching client for working in production, high availability environments.

The `SsmParameterCachingClient` class is thread safe. The recommendation is to instantiate a singleton instance of the class and reuse it across threads/requests to maximize caching.

By default, if the client has cached parameter values that have expired, but it encounters an error trying to reach SSM Parameter Store to reload them, it will return the stale cached value rather than throwing an error. This is recommended for maintaining high availability of your service, but this behavior can be disabled via the `allowStaleValues` constructor argument.

When the caching client is initialized with a `pathPrefix` set, by default, it bulk loads all parameters under the prefix when reloading the cache. In general, this is much more efficient than calling SSM for each individual parameter. However, you may want to consider disabling this behavior via the `bulkLoad` constructor argument in cases where there are many thousands of parameters under that path prefix, and the calling code only needs a few of them, since bulk loading all of the parameter values could result in unnecessary throttling and latency.

Note: In the case where you request a parameter that does not exist from the caching client, it will always attempt to load the value from AWS SSM Parameter Store. This could cause an availability issue if a code update adds a call to the client with an invalid parameter name, e.g., a misspelling. If this change made it to production, you could have many clients making calls to SSM, causing throttling which can lead to latency/availability issues. The mitigation for this is to use deployment best practices such as deploying new code updates to a development environment first and running integration tests to catch bugs like misspelled or missing parameters before deploying the code updates to production environments.

### Security Considerations

AWS SSM Parameter Store allows storing SecureString parameters, which are encrypted within AWS SSM. The caching client calls SSM with decryption enabled, meaning the decrypted values will be stored in-memory in plain text. The library does not log decrypted values.

## License Summary

This sample code is made available under the MIT-0 license. See the LICENSE file.
package software.amazon.serverless.ssmcachingclient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathRequest;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathResponse;
import software.amazon.awssdk.services.ssm.model.Parameter;
import software.amazon.awssdk.services.ssm.model.ParameterNotFoundException;
import software.amazon.awssdk.services.ssm.model.ParameterType;
import software.amazon.awssdk.services.ssm.paginators.GetParametersByPathIterable;

public class SsmParameterCachingClientTest {
    private static final String PATH_PREFIX = "/foo/";
    private static final Duration TTL = Duration.ofSeconds(1);

    private static final String STRING_PARAMETER_KEY_SUFFIX = "string";
    private static final String STRING_PARAMETER_KEY = PATH_PREFIX + STRING_PARAMETER_KEY_SUFFIX;
    private static final String STRING_PARAMETER_VALUE = "stringvalue";
    private static final Parameter STRING_PARAMETER = Parameter.builder()
            .name(STRING_PARAMETER_KEY)
            .type(ParameterType.STRING)
            .value(STRING_PARAMETER_VALUE)
            .build();

    private static final String STRING_LIST_PARAMETER_KEY_SUFFIX = "stringlist";
    private static final String STRING_LIST_PARAMETER_KEY = PATH_PREFIX + STRING_LIST_PARAMETER_KEY_SUFFIX;
    private static final String STRING_LIST_PARAMETER_VALUE = "a,b,c";
    private static final Parameter STRING_LIST_PARAMETER = Parameter.builder()
            .name(STRING_LIST_PARAMETER_KEY)
            .type(ParameterType.STRING_LIST)
            .value(STRING_LIST_PARAMETER_VALUE)
            .build();

    private static final String NOT_FOUND_PARAMETER_KEY_SUFFIX = "not here!";
    private static final String NOT_FOUND_PARAMETER_KEY = PATH_PREFIX + NOT_FOUND_PARAMETER_KEY_SUFFIX;

    @Mock
    private SsmClient ssm;
    @Mock
    private GetParametersByPathIterable iterable;

    private SsmParameterCachingClient client;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockParameters();
        client = new SsmParameterCachingClient(ssm, TTL, PATH_PREFIX);
    }

    private void mockParameters() {
        when(ssm.getParameter(
                GetParameterRequest.builder()
                        .name(STRING_PARAMETER_KEY)
                        .withDecryption(true)
                        .build()
        )).thenReturn(
                GetParameterResponse.builder()
                        .parameter(STRING_PARAMETER)
                        .build()
        );

        when(ssm.getParameter(
                GetParameterRequest.builder()
                        .name(STRING_LIST_PARAMETER_KEY)
                        .withDecryption(true)
                        .build()
        )).thenReturn(
                GetParameterResponse.builder()
                        .parameter(STRING_LIST_PARAMETER)
                        .build()
        );

        when(ssm.getParameter(
                GetParameterRequest.builder()
                        .name(NOT_FOUND_PARAMETER_KEY)
                        .withDecryption(true)
                        .build()
        )).thenThrow(ParameterNotFoundException.class);

        when(ssm.getParametersByPathPaginator(any(GetParametersByPathRequest.class))).thenReturn(iterable);
        GetParametersByPathResponse response = GetParametersByPathResponse.builder()
                .parameters(STRING_PARAMETER, STRING_LIST_PARAMETER)
                .build();
        when(iterable.stream()).thenReturn(Arrays.asList(response).stream());
    }

    @Test
    public void new_nullSsmClient() throws Exception {
        assertThatThrownBy(() -> new SsmParameterCachingClient(null, TTL, PATH_PREFIX)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void get_bulkLoad() throws Exception {
        assertThat(client.get(STRING_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_PARAMETER);
        assertThat(client.get(STRING_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_PARAMETER);
        assertThat(client.get(STRING_LIST_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_LIST_PARAMETER);
        assertThat(client.get(STRING_LIST_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_LIST_PARAMETER);

        GetParametersByPathRequest expected = GetParametersByPathRequest.builder()
                .path(PATH_PREFIX)
                .recursive(true)
                .withDecryption(true)
                .build();

        // should only have been called the first time
        verify(ssm, times(1)).getParametersByPathPaginator(expected);

        verifyNoMoreInteractions(ssm);
    }

    @Test
    public void get_bulkLoad_parameterNotFound() throws Exception {
        assertThatThrownBy(() -> client.get(NOT_FOUND_PARAMETER_KEY_SUFFIX)).isInstanceOf(ParameterNotFoundException.class);

        GetParametersByPathRequest expectedBulkRequest = GetParametersByPathRequest.builder()
                .path(PATH_PREFIX)
                .recursive(true)
                .withDecryption(true)
                .build();
        verify(ssm).getParametersByPathPaginator(expectedBulkRequest);

        GetParameterRequest expectedSingleRequest = GetParameterRequest.builder()
                .name(NOT_FOUND_PARAMETER_KEY)
                .withDecryption(true)
                .build();
        verify(ssm).getParameter(expectedSingleRequest);

        verifyNoMoreInteractions(ssm);
    }

    @Test
    public void get_bulkLoad_noPathPrefix() throws Exception {
        client = new SsmParameterCachingClient(ssm, TTL);

        assertThat(client.get(STRING_PARAMETER_KEY)).isEqualTo(STRING_PARAMETER);
        assertThat(client.get(STRING_PARAMETER_KEY)).isEqualTo(STRING_PARAMETER);
        assertThat(client.get(STRING_LIST_PARAMETER_KEY)).isEqualTo(STRING_LIST_PARAMETER);
        assertThat(client.get(STRING_LIST_PARAMETER_KEY)).isEqualTo(STRING_LIST_PARAMETER);

        GetParameterRequest expectedStringParameterRequest = GetParameterRequest.builder()
                .name(STRING_PARAMETER_KEY)
                .withDecryption(true)
                .build();
        verify(ssm, times(1)).getParameter(expectedStringParameterRequest);
        GetParameterRequest expectedStringListParameterRequest = GetParameterRequest.builder()
                .name(STRING_LIST_PARAMETER_KEY)
                .withDecryption(true)
                .build();
        verify(ssm, times(1)).getParameter(expectedStringListParameterRequest);

        verifyNoMoreInteractions(ssm);
    }

    @Test
    public void get_noBulkLoad() throws Exception {
        client = new SsmParameterCachingClient(ssm, TTL, PATH_PREFIX, true, false);

        assertThat(client.get(STRING_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_PARAMETER);
        assertThat(client.get(STRING_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_PARAMETER);
        assertThat(client.get(STRING_LIST_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_LIST_PARAMETER);
        assertThat(client.get(STRING_LIST_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_LIST_PARAMETER);

        GetParameterRequest expectedStringParameterRequest = GetParameterRequest.builder()
                .name(STRING_PARAMETER_KEY)
                .withDecryption(true)
                .build();
        verify(ssm, times(1)).getParameter(expectedStringParameterRequest);
        GetParameterRequest expectedStringListParameterRequest = GetParameterRequest.builder()
                .name(STRING_LIST_PARAMETER_KEY)
                .withDecryption(true)
                .build();
        verify(ssm, times(1)).getParameter(expectedStringListParameterRequest);

        verifyNoMoreInteractions(ssm);
    }

    @Test
    public void get_noBulkLoad_parameterNotFound() throws Exception {
        client = new SsmParameterCachingClient(ssm, TTL, PATH_PREFIX, true, false);

        assertThatThrownBy(() -> client.get(NOT_FOUND_PARAMETER_KEY_SUFFIX)).isInstanceOf(ParameterNotFoundException.class);

        GetParameterRequest expected = GetParameterRequest.builder()
                .name(NOT_FOUND_PARAMETER_KEY)
                .withDecryption(true)
                .build();
        verify(ssm).getParameter(expected);

        verifyNoMoreInteractions(ssm);
    }

    @Test
    public void get_staleCache_ssmSuccess() throws Exception {
        Clock clock = mock(Clock.class);
        client = new SsmParameterCachingClient(ssm, TTL, PATH_PREFIX, true, true, clock);

        Instant now = Instant.now();
        when(clock.instant()).thenReturn(now);
        assertThat(client.get(STRING_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_PARAMETER);

        when(clock.instant()).thenReturn(now.plus(Duration.ofSeconds(TTL.getSeconds() + 1)));
        assertThat(client.get(STRING_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_PARAMETER);

        GetParametersByPathRequest expected = GetParametersByPathRequest.builder()
                .path(PATH_PREFIX)
                .recursive(true)
                .withDecryption(true)
                .build();

        verify(ssm, times(2)).getParametersByPathPaginator(expected);

        verifyNoMoreInteractions(ssm);
    }

    @Test
    public void get_staleCache_ssmParameterNotFound() throws Exception {
        Clock clock = mock(Clock.class);
        client = new SsmParameterCachingClient(ssm, TTL, PATH_PREFIX, true, false, clock);

        Instant now = Instant.now();
        when(clock.instant()).thenReturn(now);
        assertThat(client.get(STRING_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_PARAMETER);

        when(clock.instant()).thenReturn(now.plus(Duration.ofSeconds(TTL.getSeconds() + 1)));
        when(ssm.getParameter(
                GetParameterRequest.builder()
                        .name(STRING_PARAMETER_KEY)
                        .withDecryption(true)
                        .build()
        )).thenThrow(ParameterNotFoundException.class);
        assertThatThrownBy(() -> client.get(STRING_PARAMETER_KEY_SUFFIX)).isInstanceOf(ParameterNotFoundException.class);
    }

    @Test
    public void get_staleCache_ssmOtherException_allowStaleValues() throws Exception {
        Clock clock = mock(Clock.class);
        client = new SsmParameterCachingClient(ssm, TTL, PATH_PREFIX, true, false, clock);

        Instant now = Instant.now();
        when(clock.instant()).thenReturn(now);
        assertThat(client.get(STRING_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_PARAMETER);

        when(clock.instant()).thenReturn(now.plus(Duration.ofSeconds(TTL.getSeconds() + 1)));
        when(ssm.getParameter(
                GetParameterRequest.builder()
                        .name(STRING_PARAMETER_KEY)
                        .withDecryption(true)
                        .build()
        )).thenThrow(AwsServiceException.class);

        assertThat(client.get(STRING_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_PARAMETER);
    }

    @Test
    public void get_staleCache_ssmOtherException_noAllowStaleValues() throws Exception {
        Clock clock = mock(Clock.class);
        client = new SsmParameterCachingClient(ssm, TTL, PATH_PREFIX, false, false, clock);

        Instant now = Instant.now();
        when(clock.instant()).thenReturn(now);
        assertThat(client.get(STRING_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_PARAMETER);

        when(clock.instant()).thenReturn(now.plus(Duration.ofSeconds(TTL.getSeconds() + 1)));
        when(ssm.getParameter(
                GetParameterRequest.builder()
                        .name(STRING_PARAMETER_KEY)
                        .withDecryption(true)
                        .build()
        )).thenThrow(new RuntimeException("boom!"));

        assertThatThrownBy(() -> client.get(STRING_PARAMETER_KEY_SUFFIX))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("boom!");
    }

    @Test
    public void getAsString() throws Exception {
        assertThat(client.getAsString(STRING_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_PARAMETER_VALUE);
        assertThat(client.getAsString(STRING_LIST_PARAMETER_KEY_SUFFIX)).isEqualTo(STRING_LIST_PARAMETER_VALUE);
    }

    @Test
    public void getAsStringList() throws Exception {
        assertThat(client.getAsStringList(STRING_LIST_PARAMETER_KEY_SUFFIX)).isEqualTo(Arrays.asList("a", "b", "c"));
    }

    @Test
    public void getAsStringList_wrongType() throws Exception {
        assertThatThrownBy(() -> client.getAsStringList(STRING_PARAMETER_KEY_SUFFIX)).isInstanceOf(IllegalArgumentException.class);
    }
}
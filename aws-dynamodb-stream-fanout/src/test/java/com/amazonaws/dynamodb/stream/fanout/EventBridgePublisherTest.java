package com.amazonaws.dynamodb.stream.fanout;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequest;
import software.amazon.awssdk.services.eventbridge.model.PutEventsResponse;
import software.amazon.awssdk.services.eventbridge.model.PutEventsResultEntry;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import javax.ws.rs.core.SecurityContext;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventBridgePublisherTest {

    @Mock
    private EventBridgeClient awsEventClient;

    @Mock
    private SqsClient awsSqsClient;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Principal principal;

    private EventBridgePublisher eventBridgePublisher;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        eventBridgePublisher = new EventBridgePublisher(awsEventClient, awsSqsClient);

        injectEnvironmentVariable("EBRetryLimit", "3");
        injectEnvironmentVariable("EventBusName", "default");
        injectEnvironmentVariable("SqsQueueURL", "arn:aws:sqs:us-west-2:373534280245:ddb-stream-fanout-dev-SqsQueue-1CI5PBXBMO41R");

    }

    @Test
    public void handleNormalStream() {

        DynamodbEvent normal_event = new DynamodbEvent();
        List<DynamodbEvent.DynamodbStreamRecord> normalRecords = new ArrayList<>();
        normalRecords.add(new DynamodbEvent.DynamodbStreamRecord());
        normal_event.setRecords(normalRecords);
        PutEventsResponse putEventsResponse = PutEventsResponse.builder()
                .failedEntryCount(0)
                .build();

        when(awsEventClient.putEvents(any(PutEventsRequest.class))).thenReturn(putEventsResponse);

        eventBridgePublisher.publish(normal_event);

        verify(awsEventClient).putEvents(any(PutEventsRequest.class));

    }

    @Test
    public void handleRetry() {

        DynamodbEvent normal_event = new DynamodbEvent();
        List<DynamodbEvent.DynamodbStreamRecord> normalRecords = new ArrayList<>();
        normalRecords.add(new DynamodbEvent.DynamodbStreamRecord());
        normal_event.setRecords(normalRecords);
        List<PutEventsResultEntry> putEventsResultEntryList = new ArrayList<>();
        putEventsResultEntryList.add(PutEventsResultEntry.builder()
                .errorCode("formatError")
                .errorMessage("request format error")
                .build());
        PutEventsResponse putEventsResponse = PutEventsResponse.builder()
                .failedEntryCount(1)
                .entries(putEventsResultEntryList)
                .build();

        when(awsEventClient.putEvents(any(PutEventsRequest.class))).thenReturn(putEventsResponse);

        eventBridgePublisher.publish(normal_event);

        verify(awsEventClient, times(4)).putEvents(any(PutEventsRequest.class));
        verify(awsSqsClient).sendMessage(any(SendMessageRequest.class));

    }


    private static void injectEnvironmentVariable(String key, String value)
            throws Exception {

        Class<?> processEnvironment = Class.forName("java.lang.ProcessEnvironment");

        Field unmodifiableMapField = getAccessibleField(processEnvironment, "theUnmodifiableEnvironment");
        Object unmodifiableMap = unmodifiableMapField.get(null);
        injectIntoUnmodifiableMap(key, value, unmodifiableMap);

        Field mapField = getAccessibleField(processEnvironment, "theEnvironment");
        Map<String, String> map = (Map<String, String>) mapField.get(null);
        map.put(key, value);
    }

    private static Field getAccessibleField(Class<?> clazz, String fieldName)
            throws NoSuchFieldException {

        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    private static void injectIntoUnmodifiableMap(String key, String value, Object map)
            throws ReflectiveOperationException {

        Class unmodifiableMap = Class.forName("java.util.Collections$UnmodifiableMap");
        Field field = getAccessibleField(unmodifiableMap, "m");
        Object obj = field.get(map);
        ((Map<String, String>) obj).put(key, value);
    }


}

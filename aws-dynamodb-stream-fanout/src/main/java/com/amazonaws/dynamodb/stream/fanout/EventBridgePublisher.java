package com.amazonaws.dynamodb.stream.fanout;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequest;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry;
import software.amazon.awssdk.services.eventbridge.model.PutEventsResponse;
import software.amazon.awssdk.services.eventbridge.model.PutEventsResultEntry;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class EventBridgePublisher implements EventPublisher {
    @NonNull
    private final EventBridgeClient awsEventsClient;
    @NonNull
    private final SqsClient awsSQSClient;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    @SneakyThrows(JsonProcessingException.class)
    public void publish(DynamodbEvent event) {
        final List<DynamodbEvent.DynamodbStreamRecord> ddbStreamRecords = event.getRecords();

        List<PutEventsRequestEntry> putEventsRequestEntryList = new ArrayList<>();

        String eventBusName = System.getenv("EventBusName");
        for (DynamodbEvent.DynamodbStreamRecord ddbStreamRecord : ddbStreamRecords) {
            String strRecordJSON = objectMapper.writeValueAsString(ddbStreamRecord);
            putEventsRequestEntryList.add(PutEventsRequestEntry.builder()
                    .eventBusName(eventBusName)
                    .time(Instant.now())
                    .source("com.amazonaws.dynamodb.stream.fanout")
                    .detailType("ddb_stream")
                    .detail(strRecordJSON)
                    .build());
        }

        PutEventsRequest putEventsRequest = PutEventsRequest.builder()
                .entries(putEventsRequestEntryList)
                .build();

        PutEventsResponse putEventsResult = awsEventsClient.putEvents(putEventsRequest);

        int EBRetryLimit = Integer.parseInt(System.getenv("EBRetryLimit"));
        int retryCount = 0;
        while (putEventsResult.failedEntryCount() > 0) {
            // get failed entries.
            List<PutEventsRequestEntry> failedEntriesList = new ArrayList<>();
            List<PutEventsResultEntry> PutEventsResultEntryList = putEventsResult.entries();
            for (int i = 0; i < PutEventsResultEntryList.size(); i++) {
                PutEventsRequestEntry putEventsRequestEntry = putEventsRequestEntryList.get(i);
                PutEventsResultEntry putEventsResultEntry = PutEventsResultEntryList.get(i);
                if (putEventsResultEntry.errorCode() != null) {
                    log.info(putEventsResultEntry.errorCode() + ": " + putEventsResultEntry.errorMessage());
                    failedEntriesList.add(putEventsRequestEntry);
                }
            }

            if (retryCount < EBRetryLimit) {
                // retry failed records
                log.info(putEventsResult.failedEntryCount() + " failed entry. " + "retry count: " + retryCount);
                putEventsRequestEntryList = failedEntriesList;
                putEventsRequest.toBuilder().entries(putEventsRequestEntryList).build();
                putEventsResult = awsEventsClient.putEvents(putEventsRequest);
                retryCount++;

            } else {
                // send failed records to SQS DLQ after retry limit.
                log.info("send failed records to SQS DLQ after " + EBRetryLimit + "retry.");
                for (PutEventsRequestEntry failedEntry : failedEntriesList) {
                    String queueUrl = System.getenv("SqsQueueURL");
                    awsSQSClient.sendMessage(SendMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .messageBody(failedEntry.detail())
                            .build());
                }

                return;
            }
        }
    }
}

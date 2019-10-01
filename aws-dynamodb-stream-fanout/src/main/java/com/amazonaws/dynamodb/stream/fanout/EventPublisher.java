package com.amazonaws.dynamodb.stream.fanout;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

public interface EventPublisher {
    void publish(DynamodbEvent event);
}

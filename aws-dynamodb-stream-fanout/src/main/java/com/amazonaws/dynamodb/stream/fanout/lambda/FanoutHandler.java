package com.amazonaws.dynamodb.stream.fanout.lambda;

import com.amazonaws.dynamodb.stream.fanout.dagger.FanoutComponent;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.dynamodb.stream.fanout.EventPublisher;
import com.amazonaws.dynamodb.stream.fanout.dagger.DaggerFanoutComponent;


public class FanoutHandler implements RequestHandler<DynamodbEvent, Void> {
    private final EventPublisher eventPublisher;

    public FanoutHandler() {
        FanoutComponent fanoutComponent = DaggerFanoutComponent.builder().build();
        eventPublisher = fanoutComponent.getEventPublisher();
    }

    @Override
    public Void handleRequest(DynamodbEvent dynamodbEvent, Context context) {

        eventPublisher.publish(dynamodbEvent);

        return null;
    }
}

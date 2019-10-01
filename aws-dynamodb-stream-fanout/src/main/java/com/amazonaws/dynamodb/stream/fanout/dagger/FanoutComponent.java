package com.amazonaws.dynamodb.stream.fanout.dagger;

import javax.inject.Singleton;

import com.amazonaws.dynamodb.stream.fanout.EventPublisher;

import dagger.Component;

/**
 * Dagger component.
 */
@Singleton
@Component(modules = {FanoutModule.class})
public interface FanoutComponent {
    EventPublisher getEventPublisher();
}

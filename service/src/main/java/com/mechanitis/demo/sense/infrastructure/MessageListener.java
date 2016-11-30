package com.mechanitis.demo.sense.infrastructure;

import java.util.concurrent.Flow;

@FunctionalInterface
public interface MessageListener<T> extends Flow.Subscriber<T>{
    @Override
    default void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    default void onError(Throwable throwable) {

    }

    @Override
    default void onComplete() {

    }
}

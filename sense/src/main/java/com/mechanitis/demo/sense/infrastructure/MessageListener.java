package com.mechanitis.demo.sense.infrastructure;

public interface MessageListener<T> {
    void onMessage(T message);
}

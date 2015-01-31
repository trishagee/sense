package com.mechanitis.demo.sense.message;

public interface MessageListener<T> {
    void onMessage(T message);
}

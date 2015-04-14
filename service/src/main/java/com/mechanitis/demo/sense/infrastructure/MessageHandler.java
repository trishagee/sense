package com.mechanitis.demo.sense.infrastructure;

@FunctionalInterface
public interface MessageHandler<T> {
    T processMessage(String message);
}

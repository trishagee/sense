package com.mechanitis.demo.sense.message;

import java.util.Optional;

@FunctionalInterface
public interface MessageHandler<T> {
    Optional<T> processMessage(String originalText);
}

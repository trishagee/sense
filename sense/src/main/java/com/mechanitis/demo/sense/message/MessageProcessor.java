package com.mechanitis.demo.sense.message;

import java.util.Optional;

@FunctionalInterface
public interface MessageProcessor<T> {
    Optional<T> processMessage(String originalText);
}

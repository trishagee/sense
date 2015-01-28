package com.mechanitis.demo.sense.message;

import java.util.Optional;

@FunctionalInterface
public interface MessageProcessor {
    public Optional<? extends Message> processMessage(String originalText);
}

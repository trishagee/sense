package com.mechanitis.demo.sense.client.javafx;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.concurrent.atomic.AtomicInteger;

public final class IncrementableIntegerProperty extends SimpleIntegerProperty {
    private AtomicInteger value = new AtomicInteger(0);

    public void increment() {
        super.set(value.incrementAndGet());
    }
}

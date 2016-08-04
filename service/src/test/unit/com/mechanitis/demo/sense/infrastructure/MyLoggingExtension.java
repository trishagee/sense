package com.mechanitis.demo.sense.infrastructure;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

public class MyLoggingExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        System.out.println("testInstance = [" + testInstance + "], context = [" + context + "]");
    }

}
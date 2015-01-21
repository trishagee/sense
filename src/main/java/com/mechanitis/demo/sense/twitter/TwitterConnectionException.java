package com.mechanitis.demo.sense.twitter;

public class TwitterConnectionException extends RuntimeException {
    public TwitterConnectionException(Exception e) {
        super (e);
    }

    public TwitterConnectionException(String message, Exception e) {
        super(message, e);
    }
}

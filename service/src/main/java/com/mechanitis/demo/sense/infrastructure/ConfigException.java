package com.mechanitis.demo.sense.infrastructure;

class ConfigException extends RuntimeException {
    private final int code;

    ConfigException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

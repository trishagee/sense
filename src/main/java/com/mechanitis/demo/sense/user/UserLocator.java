package com.mechanitis.demo.sense.user;

public class UserLocator {
    private static final String LOCATION_FIELD_NAME = "\"location\":\"";

    public static String getLocation(String allLocationText) {
        int indexOfLocationField = allLocationText.indexOf(LOCATION_FIELD_NAME)+ LOCATION_FIELD_NAME.length();
        int indexOfEndOfLocation = allLocationText.indexOf("\"", indexOfLocationField);
        return allLocationText.substring(indexOfLocationField, indexOfEndOfLocation);
    }
}

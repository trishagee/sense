package com.mechanitis.demo.sense.twitter;

public final class TweetParser {
    private static final String TEXT_FIELD_NAME = "\"text\":\"";
    private static final String NEXT_FIELD_NAME = "\",\"source\":\"";
    private static final String TWITTER_HANDLE_FIELD_NAME = "\"screen_name\":\"";

    private TweetParser() {
    }

    public static String getTweetMessageFrom(String fullTweet) {
        //very crude
        int indexOfTextField = fullTweet.indexOf(TEXT_FIELD_NAME);
        int indexOfEndOfText = fullTweet.indexOf(NEXT_FIELD_NAME);
        return fullTweet.substring(indexOfTextField + TEXT_FIELD_NAME.length(), indexOfEndOfText);
    }

    public static String getTwitterHandle(String allLocationText) {
        int indexOfLocationField = allLocationText.indexOf(TWITTER_HANDLE_FIELD_NAME)+ TWITTER_HANDLE_FIELD_NAME.length();
        int indexOfEndOfLocation = allLocationText.indexOf("\"", indexOfLocationField);
        return allLocationText.substring(indexOfLocationField, indexOfEndOfLocation);
    }
}

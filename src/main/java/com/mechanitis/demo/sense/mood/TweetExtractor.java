package com.mechanitis.demo.sense.mood;

public final class TweetExtractor {
    private static final String TEXT_FIELD_NAME = "\"text\":\"";
    private static final String NEXT_FIELD_NAME = "\",\"source\":\"";

    private TweetExtractor() {
    }

    public static String getTweetMessageFrom(String fullTweet) {
        //very crude
        int indexOfTextField = fullTweet.indexOf(TEXT_FIELD_NAME);
        int indexOfEndOfText = fullTweet.indexOf(NEXT_FIELD_NAME);
        return fullTweet.substring(indexOfTextField + TEXT_FIELD_NAME.length(), indexOfEndOfText);
    }
}

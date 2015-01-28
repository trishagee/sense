package com.mechanitis.demo.sense.twitter;

public final class TweetExtractor {
    private static final String TEXT_FIELD_NAME = "\"text\":\"";
    private static final String NEXT_FIELD_NAME = "\",\"source\":\"";
    private static final String USER_FIELD_NAME = "\"user\":{";

    private TweetExtractor() {
    }

    public static String getTweetMessageFrom(String fullTweet) {
        //very crude
        int indexOfTextField = fullTweet.indexOf(TEXT_FIELD_NAME);
        int indexOfEndOfText = fullTweet.indexOf(NEXT_FIELD_NAME);
        return fullTweet.substring(indexOfTextField + TEXT_FIELD_NAME.length(), indexOfEndOfText);
    }

    public static String getUserInfoFrom(String fullTweet) {
        int indexOfUserField = fullTweet.indexOf(USER_FIELD_NAME);
        int indexOfEndOfUser = fullTweet.indexOf("}", indexOfUserField);
        return fullTweet.substring(indexOfUserField + USER_FIELD_NAME.length(), indexOfEndOfUser);
    }
}

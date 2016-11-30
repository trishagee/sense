package com.mechanitis.demo.sense.twitter;

public interface TweetParser {

    static String getTweetMessageFrom(String fullTweet) {
        //very crude
        return getValueForField(fullTweet, "\"text\":\"");
    }

    static String getTwitterHandleFromTweet(String fullTweet) {
        return getValueForField(fullTweet, "\"screen_name\":\"");
    }

    private static String getValueForField(String fullTweet, String fieldName) {
        int indexOfTextField = fullTweet.indexOf(fieldName) + fieldName.length();
        int indexOfEndOfText = fullTweet.indexOf("\"", indexOfTextField);
        return fullTweet.substring(indexOfTextField, indexOfEndOfText);
    }
}

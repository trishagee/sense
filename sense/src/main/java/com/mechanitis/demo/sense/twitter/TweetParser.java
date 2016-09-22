package com.mechanitis.demo.sense.twitter;

public interface TweetParser {

    static String getTweetMessageFrom(String fullTweet) {
        String textFieldName = "\"text\":\"";
        return getFieldValue(fullTweet, textFieldName);
    }

    static String getTwitterHandleFromTweet(String fullTweet) {
        String twitterHandleFieldName = "\"screen_name\":\"";
        return getFieldValue(fullTweet, twitterHandleFieldName);
    }

    private static String getFieldValue(String fullTweet, String fieldName) {
        int indexOfTextField = fullTweet.indexOf(fieldName) + fieldName.length();
        int indexOfEndOfText = fullTweet.indexOf("\"", indexOfTextField);
        return fullTweet.substring(indexOfTextField, indexOfEndOfText);
    }
}

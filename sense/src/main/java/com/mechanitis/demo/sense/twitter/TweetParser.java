package com.mechanitis.demo.sense.twitter;

public interface TweetParser {

    static String getTweetMessageFrom(String fullTweet) {
        String textFieldName = "\"text\":\"";
        int indexOfTextField = fullTweet.indexOf(textFieldName) + textFieldName.length();
        int indexOfEndOfText = fullTweet.indexOf("\"", indexOfTextField);
        return fullTweet.substring(indexOfTextField, indexOfEndOfText);
    }

    static String getTwitterHandleFromTweet(String fullTweet) {
        String twitterHandleFieldName = "\"screen_name\":\"";
        int indexOfTwitterHandleField = fullTweet.indexOf(twitterHandleFieldName)+ twitterHandleFieldName.length();
        int indexOfEndOfTwitterHandle = fullTweet.indexOf("\"", indexOfTwitterHandleField);
        return fullTweet.substring(indexOfTwitterHandleField, indexOfEndOfTwitterHandle);
    }
}

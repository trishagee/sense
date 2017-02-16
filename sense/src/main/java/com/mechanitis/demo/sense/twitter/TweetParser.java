package com.mechanitis.demo.sense.twitter;

public interface TweetParser {

    static String getTweetMessageFrom(String fullTweet) {
        //very crude
        String textFieldName = "\"text\":\"";
        String nextFieldName = "\",\"source\":\"";
        int indexOfTextField = fullTweet.indexOf(textFieldName) + textFieldName.length();
        int indexOfEndOfText = fullTweet.indexOf(nextFieldName);
        return fullTweet.substring(indexOfTextField, indexOfEndOfText);
    }

    static String getTwitterHandleFromTweet(String fullTweet) {
        String twitterHandleFieldName = "\"screen_name\":\"";
        int indexOfTwitterHandleField = fullTweet.indexOf(twitterHandleFieldName)+ twitterHandleFieldName.length();
        int indexOfEndOfTwitterHandle = fullTweet.indexOf("\"", indexOfTwitterHandleField);
        return fullTweet.substring(indexOfTwitterHandleField, indexOfEndOfTwitterHandle);
    }
}

package com.mechanitis.demo.sense.twitter;

public interface TweetParser {

    public static String getTweetMessageFrom(String fullTweet) {
        //very crude
        String textFieldName = "\"text\":\"";
        String nextFieldName = "\",\"source\":\"";
        int indexOfTextField = fullTweet.indexOf(textFieldName) + textFieldName.length();
        int indexOfEndOfText = fullTweet.indexOf(nextFieldName);
        return fullTweet.substring(indexOfTextField, indexOfEndOfText);
    }

    public static String getTwitterHandle(String allLocationText) {
        String twitterHandleFieldName = "\"screen_name\":\"";
        int indexOfLocationField = allLocationText.indexOf(twitterHandleFieldName)+ twitterHandleFieldName.length();
        int indexOfEndOfLocation = allLocationText.indexOf("\"", indexOfLocationField);
        return allLocationText.substring(indexOfLocationField, indexOfEndOfLocation);
    }
}

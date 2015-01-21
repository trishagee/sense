package com.mechanitis.demo.sense.twitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class HttpReader
{
    public static void main(String[] args) {
        try {
            URI uri = URI.create("https://stream.twitter.com/1.1/statuses/sample.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) uri.toURL().openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");

            OAuth oauth = new OAuth();
            String signature = oauth.generateSignature();
            httpURLConnection.addRequestProperty("Authorization", "OAuth " + oauth.getAuthParams()
                                                                  + ", oauth_signature=\"" + signature + "\"");

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            reader.lines().forEach(s -> System.out.println("tweet = " + s));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

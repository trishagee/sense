package com.mechanitis.demo.sense.twitter.connector

import spock.lang.Specification
import spock.lang.Subject

import java.util.stream.Stream

class TwitterConnectionSpecification extends Specification {

    def 'should alert a listener of tweets in order'() {
        given:
        def receivedTweets = []
        @Subject def connection = new TwitterConnection({tweet -> receivedTweets.add(tweet)})

        when:
        connection.processTweets(Stream.of('first', 'second'))

        then:
        receivedTweets[0] == 'first'
        receivedTweets[1] == 'second'
    }

    def 'should not pass on deleted tweets'() {
        given:
        def receivedTweets = []
        @Subject def connection = new TwitterConnection({tweet -> receivedTweets.add(tweet)})

        def deletedTweet = "{\"delete\":{\"status\":{\"id\":545654287926190080,\"user_id\":954217777},\"timestamp_ms\":\"1421930220668\"}}\n"
        def createdTweet = "{\"created_at\":\"Thu Jan 22 12:37:00 +0000 2015\",\"id\":558241922696089600,\"text\":\"Bom diaa\", \"user\":{\"id\":2860570708}\n"

        when:
        connection.processTweets(Stream.of(deletedTweet, createdTweet))

        then:
        receivedTweets[0] == createdTweet
        receivedTweets.size() == 1
    }
}

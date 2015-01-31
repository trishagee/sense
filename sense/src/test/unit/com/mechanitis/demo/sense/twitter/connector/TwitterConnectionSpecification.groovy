package com.mechanitis.demo.sense.twitter.connector

import com.mechanitis.demo.sense.message.MessageListener
import spock.lang.Specification
import spock.lang.Subject

import java.util.stream.Stream

class TwitterConnectionSpecification extends Specification {
    @Subject
    def connection = new TwitterConnection()

    def 'should alert a listener of tweets in order'() {
        given:
        def listener = Mock(MessageListener)
        connection.addListener(listener)

        when:
        connection.processTweets(Stream.of('first', 'second'))

        then:
        1 * listener.onMessage('first')

        then:
        1 * listener.onMessage('second')
    }

    def 'should alert a late-joining listener of only tweets that happened after they started listening'() {
        given:
        def listener = Mock(MessageListener)

        when:
        connection.processTweets(Stream.of('first', 'second'))
        connection.addListener(listener)
        connection.processTweets(Stream.of('third', 'fourth'))

        then:
        1 * listener.onMessage('third')

        then:
        1 * listener.onMessage('fourth')
    }

    def 'should not pass on deleted tweets'() {
        given:
        def deletedTweet = "{\"delete\":{\"status\":{\"id\":545654287926190080,\"user_id\":954217777},\"timestamp_ms\":\"1421930220668\"}}\n"
        def createdTweet = "{\"created_at\":\"Thu Jan 22 12:37:00 +0000 2015\",\"id\":558241922696089600,\"text\":\"Bom diaa\", \"user\":{\"id\":2860570708}\n"
        def listener = Mock(MessageListener)
        connection.addListener(listener)

        when:
        connection.processTweets(Stream.of(deletedTweet, createdTweet))

        then:
        0 * listener.onMessage(deletedTweet)
        1 * listener.onMessage(createdTweet)
    }

    def 'should not alert listeners who have stopped listening'() {
        given:
        def listener = Mock(MessageListener)
        connection.addListener(listener)

        when:
        connection.processTweets(Stream.of('first', 'second'))
        connection.removeListener(listener)
        connection.processTweets(Stream.of('third', 'fourth'))

        then:
        //noinspection GroovyAssignabilityCheck
        2 * listener.onMessage(_)
        0 * listener.onMessage('third')
        0 * listener.onMessage('fourth')
    }
}

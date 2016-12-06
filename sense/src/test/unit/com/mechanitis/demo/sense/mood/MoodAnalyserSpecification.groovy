package com.mechanitis.demo.sense.mood

import spock.lang.Specification

import static com.mechanitis.demo.sense.mood.MoodAnalyser.analyseMood
import static java.lang.String.format

class MoodAnalyserSpecification extends Specification {
    private static final String TWITTER_MESSAGE_TEMPLATE = "tweet = {\"created_at\":\"Tue Jan 27 12:37:11 +0000 2015\"," +
            "\"id\":560053908144275456,\"id_str\":\"560053908144275456\"," +
            "\"text\":\"%s\",\"source\":\"twitter\"}"


    def 'should correctly identify happy messages'() {
        when:
        def moodyMessage = analyseMood(createMessage("I am so happy today"))

        then:
        moodyMessage == "HAPPY"
    }

    def 'should correctly identify sad messages'() {
        when:
        def moodyMessage = analyseMood(createMessage("I am so sad today"))

        then:
        moodyMessage == "SAD"
    }

    def 'should not have any mood for messages that are neither happy or sad'() {
        when:
        def moodyMessage = analyseMood(createMessage("I don't care"))

        then:
        moodyMessage == ""
    }

    def 'should correctly identify happy messages that are not lower case'() {
        when:
        def moodyMessage = analyseMood(createMessage("I am so Awesome today"))

        then:
        moodyMessage == "HAPPY"
    }

    def 'should correctly identify mixed messages'() {
        when:
        def moodyMessage = analyseMood(createMessage("I am so sad today it almost makes me happy"))

        then:
        moodyMessage == "SAD,HAPPY"
    }

    def 'should correctly identify mixed messages with multiple moods'() {
        when:
        def moodyMessage = analyseMood(createMessage("Yesterday I was sad sad sad, but today is awesome"))

        then:
        moodyMessage == "SAD,HAPPY"
    }

    private static String createMessage(String tweet) {
        format(TWITTER_MESSAGE_TEMPLATE, tweet)
    }
}

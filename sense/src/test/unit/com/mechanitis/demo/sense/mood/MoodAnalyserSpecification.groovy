package com.mechanitis.demo.sense.mood

import spock.lang.Ignore
import spock.lang.Specification

import static Mood.HAPPY
import static Mood.SAD
import static java.lang.String.format

class MoodAnalyserSpecification extends Specification {
    private static final String TWITTER_MESSAGE_TEMPLATE = "tweet = {\"created_at\":\"Tue Jan 27 12:37:11 +0000 2015\"," +
            "\"id\":560053908144275456,\"id_str\":\"560053908144275456\"," +
            "\"text\":\"%s\",\"source\":\"twitter\"}";


    @Ignore("4")
    def 'should correctly identify happy messages'() {
        when:
        def moodyMessage = MoodAnalyser.analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I am so happy today"))

        then:
        moodyMessage.hasMood(HAPPY)
        !moodyMessage.hasMood(SAD)
    }

    @Ignore("4")
    def 'should correctly identify happy messages that are not lower case'() {
        when:
        def moodyMessage = MoodAnalyser.analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I am so Awesome today"))

        then:
        moodyMessage.hasMood(HAPPY)
        !moodyMessage.hasMood(SAD)
    }

    @Ignore("4")
    def 'should correctly identify sad messages'() {
        when:
        def moodyMessage = MoodAnalyser.analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I am so sad today"))

        then:
        moodyMessage.hasMood(SAD)
        !moodyMessage.hasMood(HAPPY)
    }

    @Ignore("4")
    def 'should correctly identify mixed messages'() {
        when:
        def moodyMessage = MoodAnalyser.analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I am so sad today it almost makes me happy"))

        then:
        moodyMessage.hasMood(SAD)
        moodyMessage.hasMood(HAPPY)
    }

    @Ignore("4")
    def 'should not have any mood for messages that are neither happy or sad'() {
        when:
        def moodyMessage = MoodAnalyser.analyseMood(format(TWITTER_MESSAGE_TEMPLATE, "I don't care"))

        then:
        !moodyMessage.hasMood(SAD)
        !moodyMessage.hasMood(HAPPY)
        !moodyMessage.hasMood(null)
    }

}

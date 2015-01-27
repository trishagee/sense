package com.mechanitis.demo.sense.mood

import org.junit.Ignore
import spock.lang.Specification
import spock.lang.Subject

import static Mood.HAPPY
import static Mood.SAD

class MoodAnalyserSpecification extends Specification {
    @Ignore("Not implemented yet")
    def 'should only analyse English messages'() {
//        given:
//
//
//        when:
//
//        then:
    }

    def 'should correctly identify happy messages'() {
        when:
        def moodyMessage = MoodAnalyser.analyseMood("I am so happy today")

        then:
        moodyMessage.get().hasMood(HAPPY)
        !moodyMessage.get().hasMood(SAD)
    }

    def 'should correctly identify sad messages'() {
        when:
        def moodyMessage = MoodAnalyser.analyseMood("I am so sad today")

        then:
        moodyMessage.get().hasMood(SAD)
        !moodyMessage.get().hasMood(HAPPY)
    }

    def 'should correctly identify mixed messages'() {
        when:
        def moodyMessage = MoodAnalyser.analyseMood("I am so sad today it almost makes me happy")

        then:
        moodyMessage.get().hasMood(SAD)
        moodyMessage.get().hasMood(HAPPY)
    }

    def 'should not pass on messages that are neither happy nor sad'() {
        when:
        def mood = MoodAnalyser.analyseMood("I don't care")

        then:
        !mood.isPresent()
    }

}

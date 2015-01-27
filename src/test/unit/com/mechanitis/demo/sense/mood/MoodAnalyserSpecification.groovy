package com.mechanitis.demo.sense.mood

import org.junit.Ignore
import spock.lang.Specification
import spock.lang.Subject

import static Mood.HAPPY
import static Mood.SAD

class MoodAnalyserSpecification extends Specification {
    @Subject
    def moodAnalyser = new MoodAnalyser()

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
        def mood = moodAnalyser.analyse("I am so happy today")

        then:
        mood.hasMood(HAPPY)
        !mood.hasMood(SAD)
    }

    def 'should correctly identify sad messages'() {
        when:
        def mood = moodAnalyser.analyse("I am so sad today")

        then:
        mood.hasMood(SAD)
        !mood.hasMood(HAPPY)
    }

    def 'should correctly identify mixed messages'() {
        when:
        def mood = moodAnalyser.analyse("I am so sad today it almost makes me happy")

        then:
        mood.hasMood(SAD)
        mood.hasMood(HAPPY)
    }

    def 'should not pass on messages that are neither happy nor sad'() {
        when:
        def mood = moodAnalyser.analyse("I don't care")

        then:
        !mood.hasMood(HAPPY)
        !mood.hasMood(SAD)
    }

}

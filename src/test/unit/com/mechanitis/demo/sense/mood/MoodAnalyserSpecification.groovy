package com.mechanitis.demo.sense.mood

import org.junit.Ignore
import spock.lang.Specification
import spock.lang.Subject

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
        mood.get() instanceof HappyMessage
    }

    def 'should correctly identify sad messages'() {
        when:
        def mood = moodAnalyser.analyse("I am so sad today")

        then:
        mood.get() instanceof SadMessage
    }

    def 'should not pass on messages that are neither happy nor sad'() {
        when:
        Optional<MoodyMessage> mood = moodAnalyser.analyse("I don't care")

        then:
        !mood.isPresent()
    }



}

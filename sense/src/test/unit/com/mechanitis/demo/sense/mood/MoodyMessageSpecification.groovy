package com.mechanitis.demo.sense.mood

import spock.lang.Specification

class MoodyMessageSpecification extends Specification {
    def 'should return a comma delimited list of all moods on toString'() {
        given:
        def moodyMessage = new MoodyMessage([Mood.HAPPY, Mood.SAD] as Set)

        when:
        def string = moodyMessage.toString()

        then:
        string == 'HAPPY,SAD'
    }

}

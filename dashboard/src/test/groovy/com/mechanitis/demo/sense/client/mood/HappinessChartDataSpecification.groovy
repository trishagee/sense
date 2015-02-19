package com.mechanitis.demo.sense.client.mood

import spock.lang.Specification

class HappinessChartDataSpecification extends Specification {
    def 'should have ten bars with value zero before data received'() {
        when:
        def happinessChartData = new HappinessChartData()

        then:
        happinessChartData.dataSeries.data.size() == 10
        happinessChartData.dataSeries.data.each {
            assert it.YValue == 0.0
        }
    }

    def 'should increment the bar that matches the current minute if the message is happy'() {
        // this functionality would be better (and more testable) if the time element was removed from the chart
        // data - inject "now" into the constructor, and have the TweetMood know when it was created.
        given:
        def happinessChartData = new HappinessChartData()

        when:
        happinessChartData.onMessage(new TweetMood([Mood.HAPPY] as Set))

        then:
        happinessChartData.dataSeries.data[0].YValue == 1
    }


}

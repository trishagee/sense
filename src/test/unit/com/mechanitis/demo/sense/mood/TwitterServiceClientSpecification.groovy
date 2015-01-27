package com.mechanitis.demo.sense.mood

import spock.lang.Specification
import spock.lang.Subject

class TwitterServiceClientSpecification extends Specification {
    @Subject
    def twitterServiceClient = new TwitterServiceClient()

    def 'should turn full tweets into messages with mood annotations'() {
        given:
        def listener = Mock(MoodListener)
        twitterServiceClient.addListener(listener)

        when:
        twitterServiceClient.onWebSocketText(EXAMPLE_HAPPY_INPUT)

        then:
        // this is not the prettiest way to check the event is a happy event, but it will do for now
        1 * listener.onEvent({ it.toString() == 'MoodyMessage{moods=[HAPPY]}' });
    }

    def 'should not send messages with no mood'() {
        given:
        def moodListener = Mock(MoodListener)
        twitterServiceClient.addListener(moodListener)

        when:
        twitterServiceClient.onWebSocketText(EXAMPLE_MEH_INPUT)

        then:
        0 * moodListener.onEvent(_);
    }

    private static final String EXAMPLE_HAPPY_INPUT = "tweet = {\"created_at\":\"Tue Jan 27 12:37:11 +0000 2015\"," +
            "\"id\":560053908144275456,\"id_str\":\"560053908144275456\"," +
            "\"text\":\"I'm really happy today\",\"source\":\"\\u003ca href=\\\"http:\\/\\/www.twittascope.com\\\" rel=\\\"nofollow\\\"\\u003eTwittascope\\u003c\\/a\\u003e\"}"

    private static final String EXAMPLE_MEH_INPUT = "tweet = {\"created_at\":\"Tue Jan 27 12:37:11 +0000 2015\"," +
            "\"id\":560053908144275456,\"id_str\":\"560053908144275456\"," +
            "\"text\":\"I'm really something today\",\"source\":\"\\u003ca href=\\\"http:\\/\\/www.twittascope.com\\\" rel=\\\"nofollow\\\"\\u003eTwittascope\\u003c\\/a\\u003e\"}"
}

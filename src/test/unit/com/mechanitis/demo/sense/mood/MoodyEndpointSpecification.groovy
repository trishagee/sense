package com.mechanitis.demo.sense.mood

import spock.lang.Specification
import spock.lang.Subject

import javax.websocket.RemoteEndpoint
import javax.websocket.Session

class MoodyEndpointSpecification extends Specification {
    @Subject
    def moodyEndpoint = new MoodyEndpoint()

    def 'should turn full tweets into messages with mood annotations'() {
        given:
        def session = Mock(Session)
        session.isOpen() >> true

        def remoteEndpoint = Mock(RemoteEndpoint.Basic)
        session.getBasicRemote() >> remoteEndpoint

        moodyEndpoint.onWebSocketConnect(session)

        when:
        moodyEndpoint.onWebSocketText(EXAMPLE_HAPPY_INPUT)

        then:
        1 * remoteEndpoint.sendText('MoodyMessage{moods=[HAPPY]}');
    }

    private static final String EXAMPLE_HAPPY_INPUT = "tweet = {\"created_at\":\"Tue Jan 27 12:37:11 +0000 2015\"," +
            "\"id\":560053908144275456,\"id_str\":\"560053908144275456\"," +
            "\"text\":\"I'm really happy today\",\"source\":\"\\u003ca href=\\\"http:\\/\\/www.twittascope.com\\\" rel=\\\"nofollow\\\"\\u003eTwittascope\\u003c\\/a\\u003e\"}"
}

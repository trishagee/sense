package com.mechanitis.demo.sense.mood

import spock.lang.Specification
import spock.lang.Subject

import javax.websocket.RemoteEndpoint
import javax.websocket.Session

import static com.mechanitis.demo.sense.mood.Mood.HAPPY

class MoodyEndpointSpecification extends Specification {
    @Subject
    def moodyEndpoint = new MoodyEndpoint()

    def 'should forward messages with mood annotations'() {
        given:
        def session = Mock(Session)
        session.isOpen() >> true

        def remoteEndpoint = Mock(RemoteEndpoint.Basic)
        session.getBasicRemote() >> remoteEndpoint

        moodyEndpoint.onWebSocketConnect(session)

        when:
        moodyEndpoint.onMessage(MoodyMessage.Factory.createMessageFromMood(HAPPY))

        then:
        1 * remoteEndpoint.sendText('MoodyMessage{moods=[HAPPY]}');
    }
}

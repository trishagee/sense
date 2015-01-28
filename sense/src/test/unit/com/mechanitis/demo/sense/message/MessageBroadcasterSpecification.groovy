package com.mechanitis.demo.sense.message

import spock.lang.Specification
import spock.lang.Subject

import javax.websocket.RemoteEndpoint
import javax.websocket.Session

class MessageBroadcasterSpecification extends Specification {
    @Subject
    def endpoint = new MessageBroadcaster()

    def 'should forward messages with mood annotations'() {
        given:
        def session = Mock(Session)
        session.isOpen() >> true

        def remoteEndpoint = Mock(RemoteEndpoint.Basic)
        session.getBasicRemote() >> remoteEndpoint

        endpoint.onOpen(session, null)

        when:
        endpoint.onMessage(new StubMessage())

        then:
        1 * remoteEndpoint.sendText("StubMessage{}");
    }

    private static class StubMessage implements Message {
        @Override
        public String toString() {
            return "StubMessage{}";
        }
    }
}

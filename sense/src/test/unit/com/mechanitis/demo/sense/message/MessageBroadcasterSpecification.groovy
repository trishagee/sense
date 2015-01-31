package com.mechanitis.demo.sense.message

import spock.lang.Specification
import spock.lang.Subject

import javax.websocket.RemoteEndpoint
import javax.websocket.Session

class MessageBroadcasterSpecification extends Specification {
    @Subject
    def endpoint = new MessageBroadcaster()

    def 'should accept messages and publish the toString() representation'() {
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

    def 'should forward messages to all open sessions'() {
        given:
        def session = Mock(Session)
        endpoint.onOpen(session, null)
        session.isOpen() >> true

        def remoteEndpoint = Mock(RemoteEndpoint.Basic)
        session.getBasicRemote() >> remoteEndpoint

        def tweet = "Some Tweet"

        when:
        endpoint.onMessage(tweet)

        then:
        1 * remoteEndpoint.sendText(tweet);
    }

    def 'should not try to forward messages to closed sessions'() {
        given:
        def session = Mock(Session)
        endpoint.onOpen(session, null)

        when:
        endpoint.onMessage("Some Tweet")

        then:
        0 * session./get.*Remote/()
    }

    private static class StubMessage implements Message {
        @Override
        public String toString() {
            return "StubMessage{}";
        }
    }
}

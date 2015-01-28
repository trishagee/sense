package com.mechanitis.demo.sense.twitter.server

import spock.lang.Specification
import spock.lang.Subject

import javax.websocket.RemoteEndpoint
import javax.websocket.Session

class TweetsEndpointSpecification extends Specification {
    @Subject
    def endpoint = new TweetsEndpoint()

    def 'should forward tweets to all open sessions'() {
        given:
        def session = Mock(Session)
        endpoint.onOpen(session, null)
        session.isOpen() >> true

        def remoteEndpoint = Mock(RemoteEndpoint.Basic)
        session.getBasicRemote() >> remoteEndpoint

        def tweet = "Some Tweet"

        when:
        endpoint.onTweet(tweet)

        then:
        1 * remoteEndpoint.sendText(tweet);
    }

    def 'should not try to forward tweets to closed sessions'() {
        given:
        def session = Mock(Session)
        endpoint.onOpen(session, null)

        when:
        endpoint.onTweet("Some Tweet")

        then:
        0 * session./get.*Remote/()
    }


}

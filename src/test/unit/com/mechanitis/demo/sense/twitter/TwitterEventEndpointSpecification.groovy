package com.mechanitis.demo.sense.twitter

import spock.lang.Specification
import spock.lang.Subject

import javax.websocket.RemoteEndpoint
import javax.websocket.Session

class TwitterEventEndpointSpecification extends Specification {
    @Subject
    def endpoint = new TwitterEventEndpoint()

    def 'should forward tweets to all open sessions'() {
        given:
        def session = Mock(Session)
        endpoint.onWebSocketConnect(session)
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
        endpoint.onWebSocketConnect(session)

        when:
        endpoint.onTweet("Some Tweet")

        then:
        0 * session./get.*Remote/()
    }


}

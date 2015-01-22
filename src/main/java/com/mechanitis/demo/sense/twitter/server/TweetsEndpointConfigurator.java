package com.mechanitis.demo.sense.twitter.server;

import javax.websocket.server.ServerEndpointConfig;

public final class TweetsEndpointConfigurator extends ServerEndpointConfig.Configurator {
    private static final TweetsEndpoint SINGLETON_ENDPOINT = new TweetsEndpoint();

    @Override
    @SuppressWarnings("unchecked") //yep, we need an unchecked cast here
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        if (endpointClass.equals(TweetsEndpoint.class)) {
            return (T) SINGLETON_ENDPOINT;
        } else {
            throw new RuntimeException("This endpoint configurator can only be used with TweetsEndpoint, " +
                                       "not with " + endpointClass);
        }
    }

    public static TweetsEndpoint getEndpoint() {
        return SINGLETON_ENDPOINT;
    }
}

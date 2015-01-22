package com.mechanitis.demo.sense.twitter.server;

import javax.websocket.server.ServerEndpointConfig;

public class TweetsEndpointConfigurator extends ServerEndpointConfig.Configurator {
    private static final TweetsEndpoint SINGLETON_ENDPOINT = new TweetsEndpoint();

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return (T) SINGLETON_ENDPOINT;
    }

    public static TweetsEndpoint getEndpoint() {
        return SINGLETON_ENDPOINT;
    }
}

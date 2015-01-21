package com.mechanitis.demo.sense.twitter;

import javax.websocket.server.ServerEndpointConfig;

public class TwitterEndpointConfigurator extends ServerEndpointConfig.Configurator {
    private static final TwitterEventEndpoint SINGLETON_ENDPOINT = new TwitterEventEndpoint();

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return (T) SINGLETON_ENDPOINT;
    }

    public static TwitterEventEndpoint getEndpoint() {
        return SINGLETON_ENDPOINT;
    }
}

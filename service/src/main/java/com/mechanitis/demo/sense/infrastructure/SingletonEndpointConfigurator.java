package com.mechanitis.demo.sense.infrastructure;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerEndpointConfig;

public final class SingletonEndpointConfigurator extends ServerEndpointConfig.Configurator {
    private Endpoint singletonInstance;

    public SingletonEndpointConfigurator(Endpoint singletonInstance) {
        this.singletonInstance = singletonInstance;
    }

    @Override
    @SuppressWarnings("unchecked") //yep, we need an unchecked cast here
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        if (endpointClass != this.singletonInstance.getClass()) {
            throw new UnsupportedOperationException("This SingletonEndpointConfigurator only creates " +
                                                    "endpoints of class " + this.singletonInstance.getClass()+ ", "
                                                    + endpointClass + " is not supported");
        }
        return (T) singletonInstance;
    }
}


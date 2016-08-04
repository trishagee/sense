package com.mechanitis.demo.sense.infrastructure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
@ExtendWith(MyLoggingExtension.class)
public class SingletonEndpointConfiguratorTest {


    @Test
    public void shouldThrowExceptionIfIncorrectEndpointUsed() throws InstantiationException {
        SingletonEndpointConfigurator config = new SingletonEndpointConfigurator(new StubEndpoint());

        ConfigException exception = expectThrows(ConfigException.class,
                                                 () -> config.getEndpointInstance(BroadcastingServerEndpoint.class));
        assertEquals(345, exception.getCode());
    }

    private class StubEndpoint extends Endpoint {
        @Override
        public void onOpen(Session session, EndpointConfig config) {
        }
    }
}
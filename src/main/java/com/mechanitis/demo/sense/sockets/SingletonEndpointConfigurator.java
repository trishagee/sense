package com.mechanitis.demo.sense.sockets;

import com.mechanitis.demo.sense.mood.MoodyEndpoint;
import com.mechanitis.demo.sense.twitter.server.TweetsEndpoint;
import com.mechanitis.demo.sense.user.UserEndpoint;

import javax.websocket.server.ServerEndpointConfig;
import java.util.HashMap;
import java.util.Map;

public final class SingletonEndpointConfigurator extends ServerEndpointConfig.Configurator {
    private static final Map<Class<?>, Object> CLASS_TO_SINGLETON = new HashMap<>();

    static {
        CLASS_TO_SINGLETON.put(TweetsEndpoint.class, new TweetsEndpoint());
        CLASS_TO_SINGLETON.put(MoodyEndpoint.class, new MoodyEndpoint());
        CLASS_TO_SINGLETON.put(UserEndpoint.class, new UserEndpoint());
    }

    @Override
    @SuppressWarnings("unchecked") //yep, we need an unchecked cast here
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        T endpoint = (T) CLASS_TO_SINGLETON.get(endpointClass);
        if (endpoint == null) {
            throw new RuntimeException("No endpoint found for " + endpointClass);
        }
        return endpoint;
    }

    //blargh
    public static TweetsEndpoint getTweetsEndpoint() {
        return (TweetsEndpoint) CLASS_TO_SINGLETON.get(TweetsEndpoint.class);
    }

    //blargh again
    public static MoodyEndpoint getMoodyEndpoint() {
        return (MoodyEndpoint) CLASS_TO_SINGLETON.get(MoodyEndpoint.class);
    }

    //we have to stop doing this
    public static UserEndpoint getUserEndpoint() {
        return (UserEndpoint) CLASS_TO_SINGLETON.get(UserEndpoint.class);
    }
}

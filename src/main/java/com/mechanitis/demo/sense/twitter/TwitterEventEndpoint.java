package com.mechanitis.demo.sense.twitter;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ClientEndpoint
@ServerEndpoint("/tweets/")
public class TwitterEventEndpoint {
}

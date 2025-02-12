package com.sunny.system.core.service;


import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/socket/simple/{id}")
@Component
public class SocketService {

    private static final Logger logger = LoggerFactory.getLogger(SocketService.class);

    private static final ConcurrentHashMap<String, SocketService> SOCKET_MAP = new ConcurrentHashMap<>();

    Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        this.session = session;
        SOCKET_MAP.put(id, this);
    }

    @OnClose
    public void onClose(Session session) {

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        if ("ping".equals(message)) {
            session.getBasicRemote().sendText("pong");
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {

    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    public void sendInfo(@PathParam("id") String id, String message) throws IOException {
        SocketService socketService = SOCKET_MAP.get(id);
        if (socketService != null) {
            socketService.sendMessage(message);
        }
    }
}

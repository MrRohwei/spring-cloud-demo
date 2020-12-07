package com.lvbok.websocket.controller;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/myws/{userName}")
public class MyWebSocketV2 {

    private String userName;

    private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    @OnMessage
    public void onMsg(String msg) {
        sendTextMsg(userName + "：" + msg);
    }

    @OnOpen
    public void onopen(Session session, @PathParam("userName") String username) {
        userName = username;
        sessionMap.put(username, session);
        sendTextMsg(String.format("用户[%s]上线，当前在线人数：[%d]", username, sessionMap.keySet().size()));
    }

    @OnClose
    public void onclose() {
        sendTextMsg(String.format("用户[%s]离开群聊，当前在线人数：[%d]", userName, sessionMap.keySet().size()));
        sessionMap.remove(userName);
    }

    @OnError
    public void onerror(Throwable e) {
        e.printStackTrace();
    }

    private void sendTextMsg(String message) {
        for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
            Session session = entry.getValue();
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(message);
            }
        }
    }
}

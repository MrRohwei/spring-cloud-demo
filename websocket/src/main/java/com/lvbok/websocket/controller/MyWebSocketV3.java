package com.lvbok.websocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvbok.websocket.dto.SocketDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@Log4j2
@Component
@ServerEndpoint("/mywsV3/{username}")
public class MyWebSocketV3 {
    private static CopyOnWriteArraySet<MyWebSocketV3> connects = new CopyOnWriteArraySet<>();
    private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    private Session session;
    private String username;

    @OnOpen
    public void open(Session session, @PathParam("username") String username) {
        this.session = session;
        this.username = username;

        sessionMap.put(session.getId(), session);
        connects.add(this);
        log.info("[{}]加入连接，当前在线人数[{}]", username, connects.size());
        String onlinePd = String.join("，", connects.stream().map(o -> o.session.getId()).collect(Collectors.toList()));
        sendTextMsgLoop(String.format("用户[%s]成功连接，频道号为[%s]，当前在线人数[%d], 在线频道号[%s]", username, session.getId() ,connects.size(), onlinePd));
    }

    @OnClose
    public void close(Session session) {
        log.info("{}退出webSocket连接{}", session.getId(), this.username);
        if (!CollectionUtils.isEmpty(connects)) {
            connects.remove(this);
            sessionMap.remove(session.getId());
            String onlinePd = String.join("，", connects.stream().map(o -> o.session.getId()).collect(Collectors.toList()));
            sendTextMsgLoop(String.format("用户[%s]退出，频道[%s]销毁，当前在线人数[%d], 在线频道号[%s]", this.username, session.getId(), connects.size(), onlinePd));
        }
    }

    @OnError
    public void error(Throwable e) {
        log.error("webSocket连接异常：{}", e);
        e.printStackTrace();
    }

    @OnMessage
    public void message(@PathParam("username") String username, String message, Session session) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            SocketDto dto = objectMapper.readValue(message, SocketDto.class);
            // 单聊
            if (dto.getType() == 1) {
                dto.setFromUser(session.getId());
                Session fromUserSession = sessionMap.get(session.getId());
                Session toUserSession = sessionMap.get(dto.getToUser());
                log.info("频道[{}]私聊[{}]", fromUserSession.getId(), toUserSession.getId());
                if (toUserSession.isOpen()) {
//                    fromUserSession.getAsyncRemote().sendText(String.format("%s：%s", username, dto.getMessage()));
//                    toUserSession.getAsyncRemote().sendText(String.format("%s：%s", username, dto.getMessage()));
                    sendTextMsgTo(String.format("%s：%s", username, dto.getMessage()), fromUserSession);
                    sendTextMsgTo(String.format("%s：%s", username, dto.getMessage()), toUserSession);
                } else {
                    fromUserSession.getAsyncRemote().sendText("对方不在线或用户不存在");
                }
            } else {
                sendTextMsgLoop(String.format("%s：%s", username, dto.getMessage()));
            }
        } catch (JsonProcessingException e) {
            log.error("转换json失败：{}", message);
            e.printStackTrace();
        }
    }

    // 单发
    private void sendTextMsgTo(String message, Session session) {
        if (session != null && session.isOpen()) {
            session.getAsyncRemote().sendText(message);
        }
    }

    // 群发
    private void sendTextMsgLoop(String message) {
        for (MyWebSocketV3 item : connects) {
            sendTextMsgTo(message, item.session);
        }
    }
}

package com.lvbok.websocket.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lvbok.websocket.dto.SocketDto;
import com.lvbok.websocket.dto.UserDto;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@Log4j2
@Component
@ServerEndpoint("/mywsV4/{username}")
public class MyWebSocketV4 implements Serializable {
    private static final String IMG = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607428313829&di=aa16a6fc45afccdf094c0cc41626eaec&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201510%2F18%2F20151018172940_5etXi.jpeg";
    private static CopyOnWriteArraySet<MyWebSocketV4> connects = new CopyOnWriteArraySet<>();
    private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();
    private static CopyOnWriteArraySet<UserDto> userDtos = new CopyOnWriteArraySet<>();
    private static ObjectMapper OBJECTMAPPER = new ObjectMapper();

    private UserDto userDto;
    private Session session;
    private String username;

    @OnOpen
    public void open(Session session, @PathParam("username") String username) {
        this.username = username;
        this.session = session;
        sessionMap.put(session.getId(), session);
        connects.add(this);


        userDto = new UserDto();
        userDto.setUsername(username + "[" + session.getId() + "]");
        userDto.setImg(IMG);
        userDto.setCount(connects.size());
        userDtos.add(userDto);

        // 将用户列表返回给前端
        log.info("[{}]加入连接，当前在线人数[{}]", username, connects.size());
        try {
            String message = OBJECTMAPPER.writeValueAsString(userDtos);
            sendTextMsgLoop(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void close(Session session) {
        log.info("{}退出webSocket连接{}", session.getId(), this.username);
        if (!CollectionUtils.isEmpty(connects)) {
            connects.remove(this);
            sessionMap.remove(session.getId());
            Iterator<UserDto> iterator = userDtos.iterator();
            while (iterator.hasNext()) {
                UserDto dto = iterator.next();
                String username = dto.getUsername().substring(0, dto.getUsername().indexOf("["));
                if (this.username.equals(username)) {
                    userDtos.remove(dto);
                }
            }
            try {
                String message = OBJECTMAPPER.writeValueAsString(userDtos);
                sendTextMsgLoop(message);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void error(Throwable e) {
        log.error("webSocket连接异常：{}", e);
        e.printStackTrace();
    }

    @OnMessage
    public void message(@PathParam("username") String username, String message, Session session) {
//        ObjectMapper objectMapper = new ObjectMapper();

        try {
            SocketDto dto = OBJECTMAPPER.readValue(message, SocketDto.class);
            // 单聊
            if (dto.getType() == 1) {
                dto.setFromUser(session.getId());
                Session fromUserSession = sessionMap.get(session.getId());
                Session toUserSession = sessionMap.get(dto.getToUser());
                log.info("频道[{}]私聊[{}]", fromUserSession.getId(), toUserSession.getId());
                if (toUserSession.isOpen()) {
                    sendTextMsgTo(String.format("%s", dto.getMessage()), fromUserSession);
                    sendTextMsgTo(String.format("%s", dto.getMessage()), toUserSession);
                } else {
                    fromUserSession.getAsyncRemote().sendText("对方不在线或用户不存在");
                }
            } else {
                sendTextMsgLoop(String.format("%s", dto.getMessage()));
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
        for (MyWebSocketV4 item : connects) {
            sendTextMsgTo(message, item.session);
        }
    }
}

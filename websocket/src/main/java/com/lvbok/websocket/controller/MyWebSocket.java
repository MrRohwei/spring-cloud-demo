package com.lvbok.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import com.lvbok.common.CommonResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import static com.lvbok.websocket.controller.LoginController.sessionMap;

@Log4j2
@Component
@ServerEndpoint(value = "/myWebSocket/{fromUser}/{toUser}")
public class MyWebSocket {

//    private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session
            , @PathParam("fromUser") String fromUser) {
        sessionMap.put(fromUser, session);
        log.info("{}上线了\n当前在线人数：{}", fromUser, sessionMap.size());
    }

    @OnClose
    public void onClose(Session session, @PathParam("userName") String userName) {
        log.info("{}下线了！！！", userName);
        sessionMap.remove(userName);
    }

    @OnError
    public void OnError(Session session,Throwable error) {
        System.out.println(session.getId() + "抛出异常：" + error);
    }


    @OnMessage
    public void onMessage(String message, Session session
            , @PathParam("fromUser") String fromUser
            , @PathParam("toUser") String toUser) throws IOException, EncodeException {
        System.out.println(fromUser + "发送消息给: " + message);
        Iterator<Map.Entry<String, Session>> iterator = sessionMap.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String, Session> next = iterator.next();
//            Session value = next.getValue();
//            Map<String, String> messageMap = new HashMap<>();
//            value.getBasicRemote().sendText(fromUser + "说：" + message);
//        }
//        if (!sessionMap.containsKey(toUser)) {
//            return CommonResponse.fail(111111, String.format("用户%s不存在", toUserName));
//        }
        CommonResponse commonResponse = sendTo(fromUser, toUser, message);
        sessionMap.get(toUser).getBasicRemote().sendText(JSONObject.toJSONString(commonResponse));
    }

    private CommonResponse sendTo(String fromUser, String toUser, String message) {
        if (!sessionMap.containsKey(toUser)) {
            return CommonResponse.fail(111111, String.format("用户%s不存在", toUser));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromUserName", fromUser);
        jsonObject.put("toUserName", toUser);
        jsonObject.put("message", message);
        return CommonResponse.success(jsonObject);
    }
}

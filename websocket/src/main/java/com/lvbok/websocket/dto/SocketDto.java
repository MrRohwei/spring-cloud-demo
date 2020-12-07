package com.lvbok.websocket.dto;

import lombok.Data;

@Data
public class SocketDto {

    private Integer type = 0;// 0：群聊 1：单聊

    private String fromUser;// 发送者

    private String toUser;// 接收者

    private String message;// 消息内容
}

package com.lvbok.websocket.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserDto implements Serializable {

    private String username;// 当前用户名

//    @JsonBackReference
//    private CopyOnWriteArraySet<UserDto> userDtos;// 用户列表

    private String img;// 头像

    private Integer count;// 在线数

//    private Session session;// 当前用户session
}

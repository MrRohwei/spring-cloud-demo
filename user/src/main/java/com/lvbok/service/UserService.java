package com.lvbok.service;

import com.lvbok.dto.UserDto;
import com.lvbok.entity.User;

import java.util.List;

public interface UserService {

    public List<UserDto> findAllUser();
    public UserDto findById(Integer id);
    public UserDto addUser(UserDto user);
    public UserDto updateUser(UserDto user);
    public boolean deleteById(Integer id);
}

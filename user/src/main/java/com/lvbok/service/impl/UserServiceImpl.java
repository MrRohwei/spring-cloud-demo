package com.lvbok.service.impl;

import com.google.common.collect.Lists;
import com.lvbok.dto.UserDto;
import com.lvbok.entity.User;
import com.lvbok.mapper.UserMapper;
import com.lvbok.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDto> findAllUser() {
        List<User> userList = userMapper.findAll();
        List<UserDto> userDtoList = Lists.newArrayList(Lists.transform(userList, user -> {
            UserDto userDto = entity2Dto(user);
            return userDto;
        }));
        return userDtoList;
    }

    @Override
    public UserDto findById(Integer id) {
        User user = userMapper.findById(id);
        return entity2Dto(user);
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        userMapper.save(userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        userMapper.updateById(userDto);
        return userDto;
    }

    @Override
    public boolean deleteById(Integer id) {
        return userMapper.deleteById(id) > 0;
    }

    private UserDto entity2Dto(User user) {
        if (user != null) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            return userDto;
        }
        return null;
    }
}

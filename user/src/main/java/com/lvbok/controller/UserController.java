package com.lvbok.controller;

import com.lvbok.common.CommonResponse;
import com.lvbok.dto.UserDto;
import com.lvbok.entity.User;
import com.lvbok.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public CommonResponse<List<UserDto>> findAllUser(){
        return CommonResponse.success(userService.findAllUser());
    }

    @GetMapping("/findById/{id}")
    public CommonResponse<UserDto> findById(@PathVariable Integer id) {
        return CommonResponse.success(userService.findById(id));
    }

    @PostMapping("/add")
    public CommonResponse<UserDto> addUser(@RequestBody UserDto userDto) {
        return CommonResponse.success(userService.addUser(userDto));
    }

    @PostMapping("/update")
    public CommonResponse<UserDto> updateUser(@RequestBody UserDto userDto) {
        return CommonResponse.success(userService.updateUser(userDto));
    }

    @PostMapping("/delete/{id}")
    public CommonResponse<Boolean> deleteUser(@PathVariable Integer id) {
        return CommonResponse.success(userService.deleteById(id));
    }
}

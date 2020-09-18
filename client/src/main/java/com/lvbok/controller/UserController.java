package com.lvbok.controller;

import com.lvbok.common.CommonResponse;
import com.lvbok.dto.UserDto;
import com.lvbok.entity.User;
import com.lvbok.fein.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserFeignClient userFeignClient;

    @GetMapping("/findAll")
    public CommonResponse<List<User>> findAllUser(Boolean findAll) {
        if (!findAll) {
            return CommonResponse.fail();
        }
        return userFeignClient.findAllUser();
    }

    @GetMapping("/findById/{id}")
    public CommonResponse<UserDto> findById(@PathVariable Integer id) {
        CommonResponse<UserDto> byId = userFeignClient.findById(id);
        return byId;
    }

    @PostMapping("/add")
    public CommonResponse<UserDto> addUser(UserDto userDto) {
        return userFeignClient.addUser(userDto);
    }

    @PostMapping("/update")
    public CommonResponse<UserDto> updateUser(UserDto userDto) {
        return userFeignClient.updateUser(userDto);
    }

    @PostMapping("/delete/{id}")
    public CommonResponse<Boolean> deleteUser(@PathVariable Integer id) {
        return userFeignClient.deleteUser(id);
    }
}

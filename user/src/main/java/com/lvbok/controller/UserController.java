package com.lvbok.controller;

import com.lvbok.common.CommonResponse;
import com.lvbok.dto.UserDto;
import com.lvbok.entity.User;
import com.lvbok.service.UserService;
import com.lvbok.valid.Insert;
import com.lvbok.valid.Update;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public CommonResponse<List<UserDto>> findAllUser(){
        return CommonResponse.success(userService.findAllUser());
    }

    @PostMapping("/findById")
    public CommonResponse<UserDto> findById(Integer id) {
        log.info("findById接收到的ID是=========》{}", id);
        return CommonResponse.success(userService.findById(id));
    }

    @PostMapping("/add")
    public CommonResponse<UserDto> addUser(@RequestBody @Validated(value = Insert.class) UserDto userDto) {
        return CommonResponse.success(userService.addUser(userDto));
    }

    @PostMapping("/update")
    public CommonResponse<UserDto> updateUser(@Validated(value = Update.class) @RequestBody User userDto) {
//        return CommonResponse.success(userService.updateUser(userDto));
        return null;
    }
@NotNull
    @PostMapping("/delete/{id}")
    public CommonResponse<Boolean> deleteUser(@PathVariable Integer id) {
        return CommonResponse.success(userService.deleteById(id));
    }
}

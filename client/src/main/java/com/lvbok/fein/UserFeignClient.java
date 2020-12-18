package com.lvbok.fein;

import com.lvbok.common.CommonResponse;
import com.lvbok.dto.UserDto;
import com.lvbok.entity.User;
import com.lvbok.hystrix.UserFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "user", fallbackFactory = UserFeignClientFallbackFactory.class)
public interface UserFeignClient {

    @GetMapping("/user/findAll")
    public CommonResponse<List<User>> findAllUser();

    @PostMapping("/user/findById")
    public CommonResponse<UserDto> findById(@RequestParam("id") Integer id);

    @PostMapping("/user/add")
    public CommonResponse<UserDto> addUser(UserDto userDto);

    @PostMapping("/user/update")
    public CommonResponse<UserDto> updateUser(UserDto userDto);

    @PostMapping("/user/delete/{id}")
    public CommonResponse<Boolean> deleteUser(@PathVariable Integer id);
}

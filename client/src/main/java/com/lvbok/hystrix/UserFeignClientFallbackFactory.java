package com.lvbok.hystrix;

import com.lvbok.common.CommonResponse;
import com.lvbok.common.ResponseStatusEnum;
import com.lvbok.dto.UserDto;
import com.lvbok.entity.User;
import com.lvbok.fein.UserFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {

    @Override
    public UserFeignClient create(Throwable throwable) {
        return new UserFeignClient() {
            @Override
            public CommonResponse<List<User>> findAllUser() {
                log.error("获取所有用户异常: {}", throwable);
                return CommonResponse.fail(ResponseStatusEnum.FAIL.getCode(), "查询所有用户失败" + throwable.getMessage());
            }

            @Override
            public CommonResponse<UserDto> findById(Integer id) {
//                log.error("获取用户[{}]异常: {}", id, throwable);String.format("查询用户[s%]失败", id)"查询用户" + id + "失败"
                return CommonResponse.fail(ResponseStatusEnum.FAIL.getCode(), String.format("查询用户[%s]失败", id) + throwable.getMessage());
            }

            @Override
            public CommonResponse<UserDto> addUser(UserDto userDto) {
                log.error("新增用户异常: {}", throwable);
                return CommonResponse.fail(ResponseStatusEnum.FAIL.getCode(), "添加用户失败" + throwable.getMessage());
            }

            @Override
            public CommonResponse<UserDto> updateUser(UserDto userDto) {
                log.error("更新用户异常: {}", throwable);
                return CommonResponse.fail(ResponseStatusEnum.FAIL.getCode(), "更新用户失败" + throwable.getMessage());
            }

            @Override
            public CommonResponse<Boolean> deleteUser(Integer id) {
//                log.error("删除用户异常: {}", throwable);
                return CommonResponse.fail(ResponseStatusEnum.FAIL.getCode(), "删除用户失败" + throwable.getMessage());
            }
        };
    }
}

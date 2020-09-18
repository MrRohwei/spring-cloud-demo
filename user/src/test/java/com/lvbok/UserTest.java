package com.lvbok;

import com.lvbok.entity.User;
import com.lvbok.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    UserMapper userMapper;

    @Value("${server.port}")
    private int port;

    @Test
    public void testSave() {
        System.out.println(port);
        List<User> all = userMapper.findAll();
        all.forEach(user -> {
            System.out.println(user);
        });
    }
}

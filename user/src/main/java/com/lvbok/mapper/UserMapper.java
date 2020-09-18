package com.lvbok.mapper;

import com.lvbok.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public List<User> findAll();

    public User findById(Integer id);

    public void save(User user);

    public void updateById(User user);

    public int deleteById(Integer id);
}

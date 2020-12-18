package com.lvbok.entity;

import com.lvbok.valid.Insert;
import com.lvbok.valid.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotNull(message = "修改必须传ID")
    @Null(message = "新增必须为空")
    private Integer id;

    private String name;

    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}

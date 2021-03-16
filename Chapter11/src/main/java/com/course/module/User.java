package com.course.module;

import lombok.Data;

@Data
public class User {
    //里面的参数必须和
    private Integer id;
    private String name;
    private Integer age;
    private String sex;
}

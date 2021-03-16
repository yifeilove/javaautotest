package com.course.model;

import lombok.Data;

@Data
//存储用户信息的最基础的表
public class User {
    private int id;
    private String userName;
    private String password;
    private int age;
    private String sex;
    private String permission;
    private String isDelete;


    @Override
    //json的处理,toString一下就会变成JSON格式
    public String toString(){
        return (
           "{id:"+id+","+
           "userName:"+userName+","+
           "password:"+password+","+
           "age:"+age+","+
           "sex:"+sex+","+
           "permission:"+permission+","+
           "isDelete:"+isDelete+"}"
        );
    }
}

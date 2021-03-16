package com.course.utils;

import com.course.model.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

//把application.properties中的localhost和路径拼接在一起，形成测试链接
public class ConfigFile {
    //设置一个变量加载配置文件,里面的参数不需要写出properties
    //工具类我们都会设置为静态方法，不用new
    private static ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.CHINA);

    //拼接测试链接
    //getUrl()里面的参数设置为枚举类里面的名称，不让它瞎传,瞎传之后我自己控制不了了
    public static String getUrl(InterfaceName name){
        //获取localhost地址
        String address = bundle.getString("test.url");
        //获取接口路径地址
        String uri="";
        //获取最终测试地址
        String testurl;
        if (name ==InterfaceName.ADDUSER){
            uri=bundle.getString("addUser.uri");
        }
        if (name ==InterfaceName.GETUSERINFO){
            uri=bundle.getString("getUserInfo.uri");
        }
        if (name ==InterfaceName.GETUSERLIST){
            uri=bundle.getString("getUserList.uri");
        }
        if (name ==InterfaceName.UPDATEUSERINFO){
            uri=bundle.getString("updateUserInfo.uri");
        }
        if (name ==InterfaceName.LOGIN){
            uri=bundle.getString("login.uri");
        }
        testurl = address+uri;
        return testurl;


    }
}

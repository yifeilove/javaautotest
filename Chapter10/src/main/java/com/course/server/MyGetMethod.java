package com.course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//这个标签的意思是我是需要被扫描的类
@RestController
@Api(value = "/",description = "这是我全部的get方法")
public class MyGetMethod {
    //value是告诉访问路径,method是告诉请求方法
    @RequestMapping(value = "/getCookies",method = RequestMethod.GET)
    @ApiOperation(value = "通过这个方法可以获取到cookie",httpMethod = "GET")
    //getCookiess(HttpServletResponse response)这个里面response不是参数，是类，这样会返回一个页面
    public String getCookiess(HttpServletResponse response){
        //HttpServerletRequest  装请求信息的类
        //HttpServerletResponse  装响应信息的类
        //添加cookie到响应结果里面
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        return "恭喜你获得cookies信息成功一非";
    }

    /**
     * 要求客户端携带cookies访问
     */
    @RequestMapping(value = "/get/with/cookies",method = RequestMethod.GET)
    @ApiOperation(value = "要求客户端携带cookies访问",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request) {
        //1、传入cookie
        Cookie cookie = new Cookie("login","true");
        //2、发送cookies
        //getCookies()接的是数组
        Cookie[] cookies = request.getCookies();
        //3、进行判断
        //如果用户没有携带cookies
        if(Objects.isNull(cookies))
            return "你必须携带cookies信息来！！";
        //如果用户携带了cookies信息
        for (Cookie cookie1 : cookies)
            if (cookie1.getName().equals("login")&&cookie1.getValue().equals("true"))
                return "恭喜你访问成功";
        return "你必须携带cookies信息来！！";
    }

    /**
     * 开发一个需要携带参数才能访问的get请求。
     * 第一种实现方式 url：key=value&key=value
     * 我们来模拟获取商品列表这样一个接口
     */
    @RequestMapping(value = "/get/with/param",method = RequestMethod.GET)
    @ApiOperation(value = "开发一个需要携带参数才能访问的get请求方法一",httpMethod = "GET")
    //商品列表我们要返回的是一个Map，商品名称为String，价格为Integer
    //start 开始位置，end 结束位置,没有对start和end进行验证，只要传就行
    public Map<String,Integer> getlist(@RequestParam Integer start,@RequestParam Integer end){
        Map<String,Integer> myList = new HashMap<>();
        myList.put("鞋",400);
        myList.put("干脆面",1);
        myList.put("裙子",300);
        return myList;
    }


    /**
     * 第二种需要携带参数访问的get请求
     * url: ip:port/get/with/param/10/20
     */
    @RequestMapping(value = "/get/with/param/{start}/{end}",method = RequestMethod.GET)
    @ApiOperation(value = "开发一个需要携带参数才能访问的get请求方法二",httpMethod = "GET")
    public Map myGetList(@PathVariable Integer start,@PathVariable Integer end){
        Map<String,Integer> myList = new HashMap<>();
        myList.put("鞋",400);
        myList.put("干脆面",1);
        myList.put("裙子",300);
        return myList;
    }
}



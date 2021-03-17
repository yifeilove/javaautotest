package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
//第13章 TestNg+MyBatis实现数据校验
//log4j是一个功能强大的日志组件,提供方便的日志记录
@Log4j
@RestController
@Api(value = "v1",description = "用户管理系统")//swagger
//RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
@RequestMapping("v1")
public class UserManager {
    //访问数据库的对象
    @Autowired //@Autowired 可以更准确地控制应该在何处以及如何进行自动装配
    private SqlSessionTemplate template;


    @ApiOperation(value = "登录接口", httpMethod = "POST")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    //HttpServletResponse 这里是返回cookies的时候用的
    //RequestBody 请求参数，请求参数是com.course.model里面的User
    public boolean login(HttpServletResponse response, @RequestBody User user) {
        //查询数据库，拿user里面的数据去数据库查询，看是否能查到，只要能查出来一条，就说明用户在我们数据库当中
        int i =template.selectOne("login",user);
        //如果登录成功则返回“login，true”
        Cookie cookie = new Cookie("login","true");
        //bug之一
        response.addCookie(cookie);
        log.info("查询到的结果是"+i);
        //将cookie加到response当中
        if(i==1){
            log.info("登陆的用户名是："+user.getUserName());
            return true;
        }
        return false;
    }



    @ApiOperation(value = "添加用户接口是否添加成功",httpMethod = "POST")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    //上面登陆成功已经返回给了cookies了，这次添加用户得带着cookies来，所以用HttpServletRequest带过来
    public boolean addUser(HttpServletRequest request,@RequestBody User user){
        //验证cookies的方法,验证cookies是否通过
        //我们先写这个校验cookies的方法，后面别的接口都统一调用这个方法
        Boolean x = verifyCookies(request);
        //查询数据库声明一个result
        int result = 0;
        //如果x不为空,意味着User里面的用户不在数据库里面
        if(x!=null){
            result = template.insert("addUser",user);
            //log.info("添加用户失败");
        }
        if(result >0){
            log.info("添加用户的数量是："+result);
            return true;
        }
        return false;
    }



    @ApiOperation(value = "获取用户(列表)信息接口",httpMethod = "POST")
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    //返回的是个列表，里面是个User
    public List<User> getUserInfo(HttpServletRequest request,@RequestBody User user){
        Boolean x = verifyCookies(request);
        if(x==true){
            List<User> users =template.selectList("getUserInfo",user);
            log.info("getUserInfo获取到的用户信息数量是："+users.size());
            return users;
        }
        else{return null;}
    }


    @ApiOperation(value ="更新/删除用户信息接口",httpMethod = "POST")
    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    //返回的是几条更新信息
    public int updateUserInfo(HttpServletRequest request,@RequestBody User user){
        Boolean x = verifyCookies(request);
        int i = 0;
        if (x==true){
            //i 已经在上面声明了，这里不需要声明
            i =template.update("updateUserInfo",user);
        }
        log.info("updateUserInfo更新的用户信息数量是："+i);
        return i;
    }


//这个可以新建一个utis包，把这个工具放里面
    private Boolean verifyCookies(HttpServletRequest request) {
        Cookie[] cookies= request.getCookies();
        //先判断cookies是否为空
        if(Objects.isNull(cookies)){
            log.info("cookies为空");
            return false;
        }
        //for循环迭代cookies
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("login")&&cookie.getValue().equals("true")){
                log.info("cookies 携带正确");
                return true;
            }
        }
        //如果没有走进这个for循环里面，不管怎么样都是false。
        return false;
    }
}

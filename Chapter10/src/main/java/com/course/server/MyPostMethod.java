package com.course.server;

import com.course.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/",description = "这是我全部的POST请求")
@RequestMapping("/v1")
public class MyPostMethod {

    //这个变量是用来装我们cookies信息的
    private static Cookie cookie;
    //用户登陆成功获取到cookies，然后再访问其他接口获取到列表
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "登陆接口，成功后获取cookies信息",httpMethod = "POST")
    public String login(HttpServletResponse response,
                        @RequestParam(value = "userName",required = true) String userName,
                        @RequestParam(value = "Password",required = true) String password){
        if (userName.equals("xiaofeifei")&&password.equals("1234567")){
            cookie = new Cookie("login","true");
            response.addCookie(cookie);
            return "恭喜你登陆成功！";
        }
        return "用户名或者密码错误";
    }
    //验证cookies，cookies通过的话给它返回回去

    //@RequestMapping和@ApiOperation是注解
    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    @ApiOperation(value = "登陆成功之后才可以获取用户列表",httpMethod = "POST")
    public String getUserList(HttpServletRequest request,
                            @RequestBody User u){
        //获取cookies
        Cookie[] cookies = request.getCookies();
        //验证cookies是否合法
        User user;
        for (Cookie c:cookies){
            if (c.getName().equals("login")   &&c.getValue().equals("true")
            &&u.getUserName().equals("xiaofeifei")
            &&u.getPassword().equals("1234567")){
                user = new User();
                user.setName("lisi");
                user.setAge("18");
                user.setSex("Femal");
                return user.toString();
            }
        }
        return "参数不合法";
    }

}

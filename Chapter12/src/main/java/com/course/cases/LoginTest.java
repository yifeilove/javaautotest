package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {
    @BeforeTest(groups = "loginTrue",description = "测试准备工作，获取HttpClient对象")
    public void beforeTest(){
        //DefaultHttpClient这个变量一定要声明，不然会报空指针
        TestConfig.defaultHttpClient = new DefaultHttpClient();

        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSER);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        //除了上面的变量需要定义，还有httpclient变量需要定义，每一次调用接口都需要用到httpclient，这里将httpclient也放到TestConfig里面
        //DefaultHttpClient defaultHttpClient = new DefaultHttpClient();//new出来就是赋值了，赋值就可以用了
        //还有一个cookies变量的定义，也放到TestConfig
        //这里不需要写cookie，因为cookie是登录之后的接口才用的
    }
    @Test(groups = "loginTrue",description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        //将测试数据从数据库取出来
        SqlSession session = DatabaseUtil.getSqlSession();
        //这个selectOne第一个参数就是mapper/SQLMapper.xml上的数据库语句id,第二个参数就是
        LoginCase loginCase = session.selectOne("loginCase",1);
        //验证一下可以将它们打印出来
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);
        //发送请求获取结果
        String result=getResult(loginCase);
        //验证返回的结果
        //数据库验证
        //User user =  session.selectOne("login",loginCase);
        //System.out.println(user.toString());
        //断言预期验证
        Assert.assertEquals(loginCase.getExpected(),result);
    }


    @Test(groups = "loginFalse",description = "用户登录失败接口测试")
    public void loginFalse() throws IOException {
        //将数据从数据库里面取出来
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        //发送请求获取结果
        String result=getResult(loginCase);
        //验证返回的结果
        //数据库验证
        //User user =  session.selectOne("login",loginCase);
        //System.out.println(user.toString());
        //断言预期验证
        Assert.assertEquals(loginCase.getExpected(),result);
    }

    private String getResult(LoginCase loginCase) throws IOException {
        //发送请求
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        //设置参数
        JSONObject param = new JSONObject();
        param.put("userName",loginCase.getUserName());
        param.put("password",loginCase.getPassword());
        //param.put("password",loginCase.getExpected());

        //设置头信息
        post.setHeader("content-type","application/json");
        //设置请求体
        StringEntity entity= new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        String result;

        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        //获取cookies
        TestConfig.store=TestConfig.defaultHttpClient.getCookieStore();

        System.out.println(result);
        return result;
    }

}

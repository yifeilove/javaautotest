package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
//AddUserTest依赖登录成功接口，获得cookies
public class AddUserTest {
    //从测试用例的库中取出测试用例数据
    //已经依赖了，不需要再写preTest
    @Test(dependsOnGroups = "loginTrue",description = "添加用户的接口测试")
    public void addUser() throws IOException, InterruptedException {
        //用工具将测试用例中的数据从数据库取出来
        SqlSession session= DatabaseUtil.getSqlSession();
        //("addUserCase",1)中的addUserCase，是SQLMapper.xml
        AddUserCase addUserCase = session.selectOne("addUserCase",1);
        //验证
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);
        //1、发请求，获取结果
        String result=getResult(addUserCase);

        Thread.sleep(6000);

        //2、验证请求的返回结果
        //数据库验证
        User user=session.selectOne("addUser",addUserCase);
        Thread.sleep(6000);
        System.out.println(user.toString());
        //预期断言
        Thread.sleep(6000);
        Assert.assertEquals(addUserCase.getExpected(),result);
    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        //设置请求,1.创建HttpPost对象，将要请求的URL通过构造方法传入HttpPost对象。
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        //设置参数
        JSONObject param = new JSONObject();
        //将参数按照key:value的形式
        param.put("userName",addUserCase.getUserName());
        param.put("password",addUserCase.getPassword());
        param.put("sex",addUserCase.getSex());
        param.put("age",addUserCase.getAge());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());

        //1\设置头信息
        post.setHeader("content-type","application/json");

        //2\设置请求体,将设置的参数放到请求里面去
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        //调用setEntity(HttpEntity entity)方法来设置请求参数。
        post.setEntity(entity);
        //3\设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        //声明result，存放返回结果
        String result;
        //4\获取接口返回信息
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        //收到回应。 即,生成应答报文(getresponse)这个变量起作用,收到回应。
        //EntityUtils.toString获取response 的body
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        return result;
    }

}



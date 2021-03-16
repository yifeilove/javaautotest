package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
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

public class UpdateUserInfoTest {

    @Test(dependsOnGroups = "loginTrue",description = "更新用户信息的接口测试")
    public void updateUserInfo() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",1);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

        //发送请求，获取请求结果,这里返回的是个int
        int result = getResult(updateUserInfoCase);

        Thread.sleep(6000);
        //验证返回的结果
        User user = session.selectOne(updateUserInfoCase.getExpected(),updateUserInfoCase);
        Thread.sleep(3000);

        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }


    @Test(dependsOnGroups = "loginTrue",description = "删除用户信息的接口测试")
    public void deleteUserInfo() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",2);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

        int result = getResult(updateUserInfoCase);

        Thread.sleep(6000);
        //验证返回的结果
        User user = session.selectOne(updateUserInfoCase.getExpected(),updateUserInfoCase);

        Thread.sleep(3000);

        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }

    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
        //设置请求
        HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
        //设置请求参数
        JSONObject param = new JSONObject();
        //param.put("userId",updateUserInfoCase.getUserId());
        //param.put("id",updateUserInfoCase.getId());
        param.put("id",updateUserInfoCase.getUserId());
        param.put("userName",updateUserInfoCase.getUserName());
        param.put("sex",updateUserInfoCase.getSex());
        param.put("age",updateUserInfoCase.getAge());
        param.put("permission",updateUserInfoCase.getPermission());
        //param.put("expected",updateUserInfoCase.getExpected());
        param.put("isDelete",updateUserInfoCase.getIsDelete());

        //设置请求头
        post.setHeader("content-type","application/json");

        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        //执行请求
        String result;
        HttpResponse response=  TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        //if(result !=null && !result.equals("")){
            //Integer.parseInt(result);
        //}
        return Integer.parseInt(result);

    }
}

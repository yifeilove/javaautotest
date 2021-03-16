package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {

    //@Test(groups = "loginTrue",description = "获取Userid为1的用户信息")
    @Test(dependsOnGroups = "loginTrue",description = "获取Userid为1的用户信息")
    public void getUserInfo() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase",1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);
        //发送请求，获取请求结果
        JSONArray resultJsonArray =getJSONResult(getUserInfoCase);
        //Thread.sleep(6000);
        //验证返回的结果
        //先去数据库查询
        User user = session.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);

        List userList = new ArrayList();

        userList.add(user);
        JSONArray jsonArray = new JSONArray(userList);
        JSONArray jsonArray1 = new JSONArray(resultJsonArray.getString(0));
        //Assert.assertEquals(jsonArray,jsonArray1);
        //一定要加toString变成字符串再比较
        Assert.assertEquals(jsonArray.toString(),jsonArray1.toString());

    }

    private JSONArray getJSONResult(GetUserInfoCase getUserInfoCase) throws IOException {
        //设置请求
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        //设置参数
        JSONObject param = new JSONObject();
        //param.put("userid",getUserInfoCase.getUserid());
        //param.put("expected",getUserInfoCase.getExpected());
        param.put("id",getUserInfoCase.getUserid());
        //设置请求头
        post.setHeader("content-type","application/json");
        //设置请求体
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        //执行请求
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        //获取请求结果
        String result;
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        //将result转换成List，然后才能转换成JSONArray
        List resultList = Arrays.asList(result);
        JSONArray array = new JSONArray(resultList);
        return array;
    }


}

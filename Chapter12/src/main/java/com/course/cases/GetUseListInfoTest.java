package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
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
import java.util.List;

public class GetUseListInfoTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取性别为男的用户信息的接口测试")
    public void getUserList() throws IOException {

        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase",1);
        System.out.println(getUserListCase);
        System.out.println(TestConfig.getUserListUrl);

        // 发送请求获取结果
        JSONArray resultJson = getJsonResult(getUserListCase);

        // 验证
        //getUserListCase.getExpected()结果是“getUserList”，这个是mysql.xml里面的查询语句id
        List<User> userList = session.selectList(getUserListCase.getExpected(),getUserListCase);
        //循环userList
        for(User u:userList){
            System.out.println("获取的 user："+u.toString());
        }
        //集合（List）转JSON数组
        JSONArray userListJson = new JSONArray(userList);

        Assert.assertEquals(userListJson.length(),resultJson.length());

        for(int i=0; i<resultJson.length();i++){
            JSONObject expect = (JSONObject) resultJson.get(i);
            JSONObject actual = (JSONObject) userListJson.get(i);
            Assert.assertEquals(expect.toString(),actual.toString());
        }
    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        //1.创建HttpPost对象，将要请求的URL通过构造方法传入HttpPost对象。
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        //设置参数
        JSONObject param = new JSONObject();
        param.put("userName",getUserListCase.getUserName());
        param.put("age",getUserListCase.getAge());
        param.put("sex",getUserListCase.getSex());
        //param.put("expected",getUserListCase.getExpected());
        //设置请求头
        post.setHeader("content-type","application/json");
        //设置请求体
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        //执行http请求
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        //收到报文
        String result;
        result=  EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        JSONArray jsonArray = new JSONArray(result);
        return jsonArray;
    }



}

package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {
    private String url;
    //bundle方法可以加载出来配置文件,ResourceBundle bundle默认识别properties文件
    private ResourceBundle bundle;
    private CookieStore store;

    //配置文件要在test之前加载出来
    @BeforeTest
    public void beforeTest(){
        //写一个前置名称就可以，不需要把properties也写出来，是找当前resource文件下面的，如果路径不一样，需要加进去
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }
    @Test
    //从服务器拿cookies
    public void testGetCookies() throws IOException {
        String result;
        //从配置文件中拼接测试的URL
        String uri =bundle.getString("getCookies.uri");
        String testUrl = this.url+uri;

        //测试逻辑代码书写
        HttpGet get = new HttpGet(testUrl);
        //HttpClient没有获取cookies的能力，DefaultHttpClient可以获取cookies，将HttpClient换成DefaultHttpClient
        //HttpClient client = new DefaultHttpClient();
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response =client.execute(get);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        //获取cookies信息,getCookieStore可以获取
        //将cookiestore抽出来
        this.store = client.getCookieStore();
        //从store里面拿cookies放到list里面
        List<Cookie> cookieList = store.getCookies();

        //从list里面拿
        for (Cookie cookie:cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name ="+name+";cookies value ="+value);
        }
    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testWithCookisToPost() throws IOException {
        String uri = bundle.getString("test.post.with.cookies");
        String testUrl = this.url+uri;
        //声明一个Client对象，这个对象是用来进行方法的执行
        DefaultHttpClient client = new DefaultHttpClient();
        //声明一个方法，这个方法就是post方法
        HttpPost post = new HttpPost(testUrl);
        //添加参数
        JSONObject param = new JSONObject();
        param.put("name","huhansan");
        param.put("age","18");
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中 StringEntity这个方法接收的是string，直接将JSON对象param转换成string，即param.toString
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //声明一个对象来进行响应结果的存储
        String result;
        //设置cookies信息
        client.setCookieStore(this.store);
        //执行post方法
        HttpResponse response = client.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        //处理结果，就是判断返回结果是否符合预期,
            // 将返回的响应结果字符串转化成JSON对象
        JSONObject resultjson = new JSONObject(result);
           // 获取到结果值
        String success = (String) resultjson.get("huhansan");
        String status = (String) resultjson.get("status");
          // 断言 期望结果=实际结果
        Assert.assertEquals("success",success);
        Assert.assertEquals("1",status);
    }
}

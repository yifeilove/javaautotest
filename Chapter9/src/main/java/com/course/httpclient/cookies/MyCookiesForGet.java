package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {

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

    //testGetCookies方法依赖testWithCookiesToGet，把从服务器拿到的cookies带过去去发送请求
    @Test(dependsOnMethods = {"testGetCookies"})
    public void testWithCookiesToGet() throws IOException {
        String uri =bundle.getString("test.get.with.cookies");
        String testUrl = this.url+uri;

        HttpGet get = new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();

        //先设置cookies信息，再请求
        client.setCookieStore(this.store);
        //请求get
        HttpResponse response = client.execute(get);

        //获取响应的状态码
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("stausCode ="+statusCode);

        if(statusCode == 200){
            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);}}
}

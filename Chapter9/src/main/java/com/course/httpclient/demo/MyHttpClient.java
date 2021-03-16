package com.course.httpclient.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class MyHttpClient {

    @Test
    public void test1() throws IOException {
        //用来存放我们的结果
        String result;
        //http的get请求方法
        HttpGet get = new HttpGet("http://www.baidu.com");
        //需要一个client来执行,这个是用来执行get请求方法的
        HttpClient client = new DefaultHttpClient();
        //将鼠标放在execute上面可以看到client.execute生成的结果是httpresponse，所以用httpresponse来接住
        HttpResponse response=client.execute(get);
        //获取到整个响应的全体的内容response.getEntity(),然后将这个全体内容转换成字符串，用EntityUtils.toString()，这个是支持多个参数的
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
    }
}

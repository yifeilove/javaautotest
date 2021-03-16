package com.course.testng.parameter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {

    @Test(dataProvider = "data")//与下面的要传的参数的dataprovider名字一样，即data
    public void testDataProvider(String name,int age){
        System.out.println("name = "+name+";age = "+age);
    }
    //这个方法里面的数据传到上面的方法testDataProvider
    @DataProvider(name = "data")
    public Object[][] providerData(){
        Object[][] o = new Object[][]{
                {"zhangsan",18},
                {"lisi",20},
                {"wangwu",30}
        };
        return o;
    }

    //通过方法名，去传递参数
    @Test(dataProvider = "methodData")
    public void test1(String name,int age){
        System.out.println("test1111方法 name="+name+";age="+age);
    }
    @Test(dataProvider = "methodData")
    public void test2(String name,int age){
        System.out.println("test2222方法 name="+name+";age="+age);
    }
    @DataProvider(name = "methodData")
    //参数括号里面必须传Method
    public Object[][] methodDataTest(Method method){
        //先设置object为空，后面根据方法不一样，对应传不一样的值
        Object[][] result=null;
        if(method.getName().equals("test1")){
            result = new Object[][]{
                    {"zhangsan",18},
                    {"lisi",28},
                    {"wangwu",32}
            };
        } else if (method.getName().equals("test2")) {
            result = new Object[][]{
                    {"yifei",18},
                    {"liwu",20},
                    {"yanqi",22}
            };
        }
        return result;

    }
}

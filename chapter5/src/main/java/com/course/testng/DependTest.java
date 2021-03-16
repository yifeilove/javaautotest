package com.course.testng;

import org.testng.annotations.Test;

public class DependTest {

    @Test
    public void test1(){
        System.out.println("test1 run");
        throw new RuntimeException();
    }

    //test2依赖test1
    //什么是依赖，如何依赖
    //test1先执行，然后再执行test2，如果test1失败了，test2就不执行，
    @Test(dependsOnMethods = {"test1"})//这个中括号里面的名称，就是对应依赖的方法的名称，test1
    public void test2(){
        System.out.println("test2 run");

    }
}

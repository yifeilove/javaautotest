package com.course.testng;

import org.testng.annotations.*;

public class HelloWorld {
    //最基本的注解，用来把方法标记为测试的一部分
    @Test
    public void testCase1(){
        System.out.printf("Thread ID:%s%n",Thread.currentThread().getId());
        System.out.println("Test这是测试用例1");
    }

    @Test
    public void testCase2(){
        System.out.println("Test这是测试用例2");
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("beforeMethod这是在测试方法之前运行的");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("aftermethod这是在测试方法之后运行的");
    }

    @BeforeClass
    public void beforeClass(){
        System.out.println("BeforeClasszz这是在测试类之前运行的");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("afterclass这是在测试类之后运行的");
    }

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("beforesuite测试套件");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("aftersuite测试套件");
    }
}

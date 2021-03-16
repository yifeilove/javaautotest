package com.course.testng.suite;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class SuiteConfig {

    @BeforeSuite
    public void beforesuite(){
        System.out.println("测试套件");
    }

    @AfterSuite
    public void aftersuite(){
        System.out.println("测试套件");
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("beforetest测试之前运行的");
    }

    @AfterTest
    public void afterTest(){
        System.out.println("aftertest测试之后运行的");
    }
}

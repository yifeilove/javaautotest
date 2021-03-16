package com.course.testng.groups;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
//方法上分组演示
public class GroupsOnMethod {
    @Test(groups = "server")
    public void test1(){
        System.out.println("这是服务端组测试方法1111");
    }
    @Test(groups = "server")
    public void test2(){
        System.out.println("这是服务端组测试方法2222");
    }
    @Test(groups = "client")
    public void test3(){
        System.out.println("这是客服端组测试方法333");
    }
    @Test(groups = "client")
    public void test4(){
        System.out.println("这是客服端组测试方法444");
    }
    @BeforeGroups("server")
    public void beforeGroupsOnServer(){
        System.out.println("beforegroupsonserver 这是服务端组运行之前运行的方法");
    }
    @AfterGroups("server")
    public void afterGroupsOnServer(){
        System.out.println("afterGroupsOnServer 这是服务端组运行之后运行的方法!!!");
    }

    @BeforeGroups("client")
    public void beforeGroupsOnClient(){
        System.out.println("beforeGroupsOnClient 这是客户端组运行之前运行的方法");
    }
    @AfterGroups("client")
    public void afterGroupsOnClient(){
        System.out.println("afterGroupsOnClient 这是客户端组运行之后运行的方法!!!");
    }
}

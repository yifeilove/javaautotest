package com.course.testng.groups;

import org.testng.annotations.Test;

//类分组测试
@Test(groups = "stu")
public class GroupsOnClass1 {

    public void stu1(){
        System.out.println("GroupsOnClass1中的study1运行");
    }

    public void stu2(){
        System.out.println("GroupsOnClass1中的study222运行");
    }
}

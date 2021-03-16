package com.course.testng.groups;

import org.testng.annotations.Test;

//在类分组中，Test标签不写在方法上，写在类上
@Test(groups ="teacher" )
public class GroupsOnClass3 {

    public void teacher1(){
        System.out.println("GroupsOnClass3中的teacher1运行");
    }

    public void teacher2(){
        System.out.println("GroupsOnClass3中的teacher2运行");
    }
}

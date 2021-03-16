package com.course.testng.multiThread;

import org.testng.annotations.Test;

public class MultiThreadOnAnnotation {

    @Test(invocationCount = 10,threadPoolSize = 3)//用10个线程来执行
    public void test1(){
        System.out.println(1);
        System.out.printf("Thread ID:%s%n",Thread.currentThread().getId());
    }
}

package com.course.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

//工具类写的都是静态的方法
//读取数据库的测试用例，我们给写一个工具，就不用每次都去写同样的读取代码
public class DatabaseUtil {

    public static SqlSession getSqlSession() throws IOException {
        //1.获取配置的资源文件
        //IO异常，万一是配置文件读取错误等，抛出异常
        Reader reader = Resources.getResourceAsReader("databaseConfig.xml");
        //2.把读取的文件build出来，build返回的是一个SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        //3.sqlSession就是能够执行配置文件中的sql语句。
        SqlSession SqlSession = factory.openSession();
        return SqlSession;
    }
}

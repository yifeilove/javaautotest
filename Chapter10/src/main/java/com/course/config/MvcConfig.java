//package com.course.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//public class MvcConfig extends WebMvcConfigurerAdapter {
//
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(
//                new CheckLoginInterceptor())
//                .addPathPatterns("/*")
//                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
//    }
//
//    public CheckLoginInterceptor checkLoginInterceptor(){
//        return new CheckLoginInterceptor();
//    }
//
//}

package com.course.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        //.apiInfo(apiInfo())里面的apiInfo()是个方法
        //.pathMapping("/")里面的"/"是匹配所有
        //.paths(PathSelectors.regex("/.*"))里面的regex是正则匹配，/.*是匹配所有
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .paths(PathSelectors.regex("/.*"))
                .build();

    }

    //.contact() 联系人
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("UserManager Service API")
                .contact(new Contact("yifei","","lijinlivip@163.com"))
                .description("this is UserManager service api")
                .version("1.0")
                .build();
    }
}

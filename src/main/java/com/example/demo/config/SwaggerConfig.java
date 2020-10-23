package com.example.demo.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {
    @Bean  //配置swagger的bean
//    public Docket docket(Environment environment){
//        //RequestHandlerSelectors配置要扫描接口打方式
//        //any()全部
//        //none()都不
//        //withClassAnnotation扫描类上的注解
//        //withMethodAnnotation扫描方法上的注解
//        //设置演示swagger环境
//        //多个分组健多个Docket就行
//        Profiles profile= Profiles.of("dev");
//        //通过environment.acceptsProfiles是否处于设定的环境当中
//        boolean flag=environment.acceptsProfiles(profile);
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
//                .enable(flag)//是否开启swagger
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controll"))
//                //配置路径
//                //.paths(PathSelectors.ant("/user/**"))
//                .build();
//    }
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controll"))
                .paths(PathSelectors.any())
                .build();
    }
    //配置信息
    public ApiInfo apiInfo(){
        //作者信息
        Contact DEFAULT_CONTACT = new Contact("邓龙术", "", "784563629@qq.com");
        return new ApiInfo(
                "邓龙术APi文档",
                "再小的帆也能杨帆启航",
                "1.0",
                "urn:tos",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }


}

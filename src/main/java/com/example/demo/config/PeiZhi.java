package com.example.demo.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PeiZhi implements WebMvcConfigurer {
        //添加视图控制
        public void addViewControllers(ViewControllerRegistry registry) {
//            registry.addViewController("/").setViewName("login");
//            registry.addViewController("/login.html").setViewName("login");
//            registry.addViewController("/main.html").setViewName("index");
        }
        //自定义国际化生效
       // @Bean
//        public LocaleResolver localeResolver(){
//            return new Logun();
//        }


//    @Bean
//    public void addInterceptors(InterceptorRegistry registry) {
//           registry.addInterceptor(new LoginHandlerInter()).addPathPatterns("/**").excludePathPatterns("/login.html","/","/user/login");
//    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInter()).addPathPatterns("/**").excludePathPatterns("/login.html","/","/SysUser/Loging","/SysUser/Login","/SysUser/getImg","/css/**","/js/**","/images/**","/lib/**");
    }

}

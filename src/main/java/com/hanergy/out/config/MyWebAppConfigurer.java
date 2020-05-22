package com.hanergy.out.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

//@EnableWebMvc     //添加EnableWebMvc注解之后拦截器不拦截路径不生效
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {

    // 接口拦截
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludes = new ArrayList<>();
        excludes.add("swagger-ui.html");
        excludes.add("/swagger-resources/configuration/ui");
        final String[] notLoginInterceptPaths = {"/static/**","**/login","/cs-web/test/**"};
        //添加登录验证过滤器 拦截所有的用户请求  /cs-web/swagger-resources/configuration/ui
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/test/**","/cs-web/swagger**"); //拦截排除生效
        //.addPathPatterns("/cs-web/**")
        //这种方式拦截排除不生效
//                .excludePathPatterns(notLoginInterceptPaths);
    }

    //整合swagger2在线文档
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }
}

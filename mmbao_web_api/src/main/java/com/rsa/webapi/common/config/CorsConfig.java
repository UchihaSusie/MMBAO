package com.rsa.webapi.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author : gfs
 * @className: CorsConfig
 * @description: 配置跨域
 * @date: 2024/5/23 9:38
 * @version: 0.1
 * @since: jdk11
 */
@Configuration
public class CorsConfig {

    private CorsConfiguration buildConfig(){
        //结果对象
        CorsConfiguration configuration = new CorsConfiguration();
        //配置各种规则
        //任何域都允许
        configuration.addAllowedOrigin("http://192.168.0.158:5173");
        //任何的头信息,都允许
        configuration.addAllowedHeader("*");
        //任何的请求方法都允许
        configuration.addAllowedMethod("*");
        //支持安全证书,默认情况下,服务器人为cookie是一个不安全的因素
        //cookie是存在浏览器端的,可以被js修改,所以服务端不信任cookie
        //cookie的ID 就是 Session的ID
        configuration.setAllowCredentials(true);
        //预检请求的有效期，单位为秒。设置maxage，可以避免每次都发出预检请求
        configuration.setMaxAge(3600L);

        return configuration;
    }

    @Bean
    public CorsFilter corsFilter(){
        //预先的配置
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //所有的请求,都过滤
        source.registerCorsConfiguration("/**",buildConfig());
        return new CorsFilter(source);
    }
}

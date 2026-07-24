package com.example.taskmanager.config;

import com.example.taskmanager.filter.AuthFilter;
import com.example.taskmanager.filter.LogFilter;
import com.example.taskmanager.filter.RequestIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<RequestIdFilter> requestIdFilter(){
        FilterRegistrationBean<RequestIdFilter> bean= new FilterRegistrationBean<>();
        bean.setFilter(new RequestIdFilter());
        bean.setOrder(1);
        bean.addUrlPatterns("/api/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<LogFilter> logFilter(){
        FilterRegistrationBean<LogFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LogFilter());
        bean.setOrder(2);
        bean.addUrlPatterns("/api/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter(){
        FilterRegistrationBean<AuthFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new AuthFilter());
        bean.setOrder(3);
        bean.addUrlPatterns("/api/*");
        return bean;
    }


}

package com.stackroute.apigateway.config;

import com.stackroute.apigateway.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> filter = new FilterRegistrationBean<JwtFilter>();
        filter.setFilter(new JwtFilter());
        filter.addUrlPatterns(
                "/favorite/*",
                "/user/api/v1/users/delete",
                "/user/api/v1/users/auth",
                "/user/api/v1/users/image");
        return filter;
    }
}

package com.irunninglog.spring;

import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.date.ApiDate;
import com.irunninglog.math.ApiMath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.irunninglog.spring")
public class SpringConfig {

    @Bean
    @Scope("prototype")
    public IRequest request() {
        return new DefaultRequest();
    }

    @Bean
    @Scope("prototype")
    public IResponse response() {
        return new DefaultResponse();
    }

    @Bean
    public ApiDate apiDate() {
        return new ApiDate();
    }

    @Bean
    public ApiMath apiMath() {
        return new ApiMath();
    }

}

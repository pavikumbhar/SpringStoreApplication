package com.pavikumbhar.javaheart.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
     public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
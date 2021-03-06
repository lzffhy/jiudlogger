package com.jiud;

import com.jiud.aop.SysLogAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
@EnableConfigurationProperties(JiudLoggerProperties.class)
@ConditionalOnProperty(prefix = "jiudlogger",name = "isopen",havingValue = "true")
public class JiudLoggerConfiguration {

    @Autowired
    private JiudLoggerProperties jiudLoggerProperties;

    @Bean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }
}

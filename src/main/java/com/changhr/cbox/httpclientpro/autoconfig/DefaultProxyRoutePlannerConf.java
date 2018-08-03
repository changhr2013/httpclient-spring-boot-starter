package com.changhr.cbox.httpclientpro.autoconfig;

import com.changhr.cbox.httpclientpro.properties.DefaultProxyRoutePlannerProperties;
import com.changhr.cbox.httpclientpro.service.DefaultProxyRoutePlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HttpClient 代理配置
 *
 * @author changhr
 * @create 2018-08-01 10:05
 */
@Configuration
@ConditionalOnClass(DefaultProxyRoutePlannerService.class)
@EnableConfigurationProperties(DefaultProxyRoutePlannerProperties.class)
public class DefaultProxyRoutePlannerConf {

    private final DefaultProxyRoutePlannerProperties properties;

    @Autowired
    public DefaultProxyRoutePlannerConf(DefaultProxyRoutePlannerProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultProxyRoutePlannerService defaultProxyRoutePlannerService(){
        return new DefaultProxyRoutePlannerService(properties.getProxyHost(), properties.getProxyPort());
    }
}

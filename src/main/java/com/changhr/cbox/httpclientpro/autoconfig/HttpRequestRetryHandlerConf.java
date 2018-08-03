package com.changhr.cbox.httpclientpro.autoconfig;

import com.changhr.cbox.httpclientpro.properties.HttpRequestRetryHandlerProperties;
import com.changhr.cbox.httpclientpro.service.HttpRequestRetryHandlerService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HTTP 请求重试配置
 *
 * @author changhr
 * @create 2018-08-01 9:30
 */
@Configuration
@ConditionalOnClass(HttpRequestRetryHandlerService.class)
@EnableConfigurationProperties(HttpRequestRetryHandlerProperties.class)
public class HttpRequestRetryHandlerConf {

    private final HttpRequestRetryHandlerProperties properties;

    public HttpRequestRetryHandlerConf(HttpRequestRetryHandlerProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    HttpRequestRetryHandlerService httpRequestRetryHandlerService(){
        return new HttpRequestRetryHandlerService(properties.getRetryCount());
    }
}

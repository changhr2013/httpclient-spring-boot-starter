package com.changhr.cbox.httpclientpro.autoconfig;

import com.changhr.cbox.httpclientpro.properties.RequestConfigProperties;
import com.changhr.cbox.httpclientpro.service.RequestConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 请求配置类配置
 *
 * @author changhr
 * @create 2018-08-01 10:22
 */
@Configuration
@ConditionalOnClass(RequestConfigService.class)
@EnableConfigurationProperties(RequestConfigProperties.class)
public class RequestConfigConf {

    private final RequestConfigProperties properties;

    @Autowired
    public RequestConfigConf(RequestConfigProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestConfigService requestConfigService(){
        return new RequestConfigService(properties.getConnectTimeout(),
                properties.getConnectionRequestTimeout(),
                properties.getSocketTimeout());
    }
}

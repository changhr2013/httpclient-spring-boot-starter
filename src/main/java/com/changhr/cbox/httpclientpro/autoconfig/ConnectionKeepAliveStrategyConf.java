package com.changhr.cbox.httpclientpro.autoconfig;

import com.changhr.cbox.httpclientpro.properties.ConnectionKeepAliveStrategyProperties;
import com.changhr.cbox.httpclientpro.service.ConnectionKeepAliveStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * httpclient 连接保持策略配置
 *
 * @author changhr
 * @create 2018-07-31 17:33
 */
@Configuration
@ConditionalOnClass(ConnectionKeepAliveStrategyService.class)
@EnableConfigurationProperties(ConnectionKeepAliveStrategyProperties.class)
public class ConnectionKeepAliveStrategyConf {

    private final ConnectionKeepAliveStrategyProperties properties;

    @Autowired
    public ConnectionKeepAliveStrategyConf(ConnectionKeepAliveStrategyProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionKeepAliveStrategyService connectionKeepAliveStrategyService(){
        return new ConnectionKeepAliveStrategyService(properties.getKeepAliveTime());
    }
}

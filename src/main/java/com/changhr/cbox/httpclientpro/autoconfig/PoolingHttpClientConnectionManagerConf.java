package com.changhr.cbox.httpclientpro.autoconfig;

import com.changhr.cbox.httpclientpro.properties.PoolingHttpClientConnectionManagerProperties;
import com.changhr.cbox.httpclientpro.service.PoolingHttpClientConnectionManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HttpClient 连接池管理配置
 *
 * @author changhr
 * @create 2018-08-01 9:40
 */
@Configuration
@ConditionalOnClass(PoolingHttpClientConnectionManagerService.class)
@EnableConfigurationProperties(PoolingHttpClientConnectionManagerProperties.class)
public class PoolingHttpClientConnectionManagerConf {

    private final PoolingHttpClientConnectionManagerProperties properties;

    @Autowired
    public PoolingHttpClientConnectionManagerConf(PoolingHttpClientConnectionManagerProperties properties){
        this.properties = properties;
    }

    /**
     * 实例化一个连接池管理器，设置最大连接数、并发连接数
     *
     * @ConditionalOnClass 当classpath下发现该类的情况下进行自动配置。
     * @ConditionalOnMissingBean 当Spring Context中不存在该Bean时。
     * @return
     */
    @Bean(name = "httpClientConnectionManager")
    @ConditionalOnMissingBean
    PoolingHttpClientConnectionManagerService poolingHttpClientConnectionManagerService(){
        return new PoolingHttpClientConnectionManagerService(
                properties.getMaxTotal(),
                properties.getDefaultMaxPerRoute(),
                properties.getTimeToLive(),
                properties.getValidateAfterInactivity()
        );
    }
}

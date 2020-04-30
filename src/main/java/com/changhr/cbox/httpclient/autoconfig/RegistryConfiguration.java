package com.changhr.cbox.httpclient.autoconfig;

import com.changhr.cbox.httpclient.http.CustomTrustManager;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * @author changhr
 * @create 2018-08-01 11:54
 */
@Configuration
public class RegistryConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CustomTrustManager customTrustManager() {
        return new CustomTrustManager();
    }

    @Bean("sslContext")
    @ConditionalOnMissingBean
    public SSLContext sslContext() {
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{customTrustManager()}, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
        return sslContext;
    }

    @Bean
    @ConditionalOnMissingBean
    public SSLConnectionSocketFactory sslConnectionSocketFactory() {
        return new SSLConnectionSocketFactory(
                sslContext(),
                new String[]{"TLSv1", "SSLv3"},
                null,
                // 客户端验证服务器身份的策略
                NoopHostnameVerifier.INSTANCE
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public Registry<ConnectionSocketFactory> socketFactoryRegistry() {
        return RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", sslConnectionSocketFactory())
                .build();
    }
}

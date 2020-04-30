package com.changhr.cbox.httpclient.autoconfig;

import com.changhr.cbox.httpclient.HttpAsyncInvoker;
import com.changhr.cbox.httpclient.HttpConnectionManager;
import com.changhr.cbox.httpclient.HttpInvoker;
import com.changhr.cbox.httpclient.properties.HttpClientProperties;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @author changhr
 * @create 2020-04-30 14:10
 */
@Configuration
@Import({RegistryConfiguration.class})
@EnableConfigurationProperties({HttpClientProperties.class})
public class HttpInvokerConfiguration {

    @Autowired
    private HttpClientProperties properties;

    @Autowired
    private Registry<ConnectionSocketFactory> socketFactoryRegistry;

    @Bean
    @ConditionalOnMissingBean
    public RequestConfig requestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(properties.getRequest().getConnectionRequestTimeout())
                .setConnectTimeout(properties.getRequest().getConnectTimeout())
                .setSocketTimeout(properties.getRequest().getSocketTimeout())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultProxyRoutePlanner defaultProxyRoutePlanner() {
        HttpHost proxy = new HttpHost(
                properties.getProxy().getProxyHost(),
                properties.getProxy().getProxyPort());
        return new DefaultProxyRoutePlanner(proxy);
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
                // Honor 'keep-alive' header
                HeaderElementIterator iterator = new BasicHeaderElementIterator(
                        httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (iterator.hasNext()) {
                    HeaderElement headerElement = iterator.nextElement();
                    String param = headerElement.getName();
                    String value = headerElement.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {

                        }
                    }
                }
                return properties.getKeepAliveTime() * 1000;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpRequestRetryHandler httpRequestRetryHandler() {
        // 请求重试
        final int retryCount = properties.getRetryCount();
        return new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                // 如果已经重试了 retryCount 次，就放弃
                if (executionCount >= retryCount) {
                    return false;
                }
                // 如果服务器丢掉了连接，那么就重试
                if (exception instanceof NoHttpResponseException) {
                    return true;
                }
                // 不要重试 SSL 握手异常
                if (exception instanceof SSLHandshakeException) {
                    return false;
                }
                // 超时
                if (exception instanceof InterruptedIOException) {
                    return false;
                }
                // 目标服务器不可达
                if (exception instanceof UnknownHostException) {
                    return false;
                }
                // 连接被拒绝
                if (exception instanceof ConnectTimeoutException) {
                    return false;
                }
                // SSL 握手异常
                if (exception instanceof SSLException) {
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager httpClientConnectionManager =
                new PoolingHttpClientConnectionManager(
                        socketFactoryRegistry,
                        null,
                        null,
                        null,
                        properties.getManager().getTimeToLive(), TimeUnit.SECONDS);
        // 最大连接数
        httpClientConnectionManager.setMaxTotal(properties.getManager().getMaxTotal());
        // 并发数
        httpClientConnectionManager.setDefaultMaxPerRoute(properties.getManager().getDefaultMaxPerRoute());
        // 定义不活动的时间段（单位：ms），之后必须在租用给使用者之前重新验证持久连接。
        // 传递给此方法的非正值会禁用连接验证。
        // 此检查有助于检测已经变为陈旧（半关闭）的连接，同时在池中保持不活动状态。
        httpClientConnectionManager.setValidateAfterInactivity(properties.getManager().getValidateAfterInactivity());

        return httpClientConnectionManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpInvoker httpInvoker() {
        HttpConnectionManager manager = new HttpConnectionManager(requestConfig(), poolingHttpClientConnectionManager());
        return new HttpInvoker(manager);
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpAsyncInvoker httpAsyncInvoker() {
        HttpConnectionManager manager = new HttpConnectionManager(requestConfig(), poolingHttpClientConnectionManager());
        return new HttpAsyncInvoker(manager);
    }
}

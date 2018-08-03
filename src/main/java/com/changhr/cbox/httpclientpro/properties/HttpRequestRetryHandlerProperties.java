package com.changhr.cbox.httpclientpro.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Http请求重试参数配置
 *
 * @author changhr
 * @create 2018-08-03 9:30
 */
@ConfigurationProperties("http.client.retry")
public class HttpRequestRetryHandlerProperties {

    private int retryCount = 2;

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
}

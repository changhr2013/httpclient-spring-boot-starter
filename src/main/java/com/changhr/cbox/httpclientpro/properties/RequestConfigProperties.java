package com.changhr.cbox.httpclientpro.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RequesConfig属性类
 *
 * @author changhr
 * @create 2018-08-03 9:19
 */
@ConfigurationProperties("http.client.request")
public class RequestConfigProperties {

    private int connectTimeout = 1000;
    private int connectionRequestTimeout = 500;
    private int socketTimeout = 10000;

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}

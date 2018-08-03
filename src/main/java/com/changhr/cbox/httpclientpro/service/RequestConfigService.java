package com.changhr.cbox.httpclientpro.service;

import org.apache.http.client.config.RequestConfig;

/**
 * RequestConfig服务类
 *
 * @author changhr
 * @create 2018-08-03 9:22
 */
public class RequestConfigService {

    private int connectTimeout;
    private int connectionRequestTimeout;
    private int socketTimeout;

    public RequestConfigService(int connectTimeout, int connectionRequestTimeout, int socketTimeout) {
        this.connectTimeout = connectTimeout;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.socketTimeout = socketTimeout;
    }

    public RequestConfig getRequestConfig(){
        return RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .build();
    }
}

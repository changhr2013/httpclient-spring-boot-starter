package com.changhr.cbox.httpclientpro.service;

import org.apache.http.HttpHost;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

/**
 * 代理配置服务
 *
 * @author changhr
 * @create 2018-08-03 9:55
 */
public class DefaultProxyRoutePlannerService {

    private String proxyHost;
    private int proxyPort;

    public DefaultProxyRoutePlannerService(String proxyHost, int proxyPort) {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
    }

    public DefaultProxyRoutePlanner getDefaultProxyRoutePlanner(){
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        return new DefaultProxyRoutePlanner(proxy);
    }
}

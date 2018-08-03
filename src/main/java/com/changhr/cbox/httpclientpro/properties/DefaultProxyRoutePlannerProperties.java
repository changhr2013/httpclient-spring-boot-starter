package com.changhr.cbox.httpclientpro.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 代理配置属性类
 *
 * @author changhr
 * @create 2018-08-03 9:53
 */
@ConfigurationProperties("http.client.proxy")
public class DefaultProxyRoutePlannerProperties {

    private String proxyHost = "localhost";

    private int proxyPort = 8090;

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }
}

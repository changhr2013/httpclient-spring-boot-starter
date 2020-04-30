package com.changhr.cbox.httpclient.properties;

/**
 * @author changhr
 * @create 2020-04-30 12:00
 */
public class ProxyProp {

    private String proxyHost = "127.0.0.1";

    private int proxyPort = 8080;

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

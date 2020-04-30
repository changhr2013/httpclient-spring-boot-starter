package com.changhr.cbox.httpclient.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author changhr
 * @create 2020-04-30 11:49
 */
@ConfigurationProperties("http.client")
public class HttpClientProperties {

    /**
     * 连接管理属性
     */
    private ManagerProp manager = new ManagerProp();

    private RequestProp request = new RequestProp();

    private ProxyProp proxy = new ProxyProp();

    private int retryCount = 3;

    private int keepAliveTime = 30;

    public ManagerProp getManager() {
        return manager;
    }

    public void setManager(ManagerProp manager) {
        this.manager = manager;
    }

    public RequestProp getRequest() {
        return request;
    }

    public void setRequest(RequestProp request) {
        this.request = request;
    }

    public ProxyProp getProxy() {
        return proxy;
    }

    public void setProxy(ProxyProp proxy) {
        this.proxy = proxy;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }
}

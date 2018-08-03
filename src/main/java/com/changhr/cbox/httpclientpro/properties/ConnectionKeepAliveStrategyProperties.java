package com.changhr.cbox.httpclientpro.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 连接保持策略属性类
 *
 * @author changhr
 * @create 2018-08-03 9:41
 */
@ConfigurationProperties("http.client.alive")
public class ConnectionKeepAliveStrategyProperties {

    private int keepAliveTime = 30;

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }
}

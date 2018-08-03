package com.changhr.cbox.httpclientpro.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 连接池属性配置
 *
 * @author m4000e
 * @create 2018-08-02 17:49
 */
@ConfigurationProperties("http.client.manager")
public class PoolingHttpClientConnectionManagerProperties {

    private int maxTotal = 100;
    private int defaultMaxPerRoute = 20;
    private int timeToLive = 60;
    private int validateAfterInactivity = 30000;

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getValidateAfterInactivity() {
        return validateAfterInactivity;
    }

    public void setValidateAfterInactivity(int validateAfterInactivity) {
        this.validateAfterInactivity = validateAfterInactivity;
    }
}

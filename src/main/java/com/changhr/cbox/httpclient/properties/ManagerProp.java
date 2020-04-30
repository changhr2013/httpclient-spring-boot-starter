package com.changhr.cbox.httpclient.properties;

/**
 * 连接管理属性
 *
 * @author changhr
 * @create 2020-04-30 11:50
 */
public class ManagerProp {

    /** 最大连接数 */
    private int maxTotal = 100;

    /** 并发数 */
    private int defaultMaxPerRoute = 20;

    /** 连接存活时间（单位：s） */
    private int timeToLive = 60;

    /**
     * 可用空闲连接过期时间，重用空闲连接时会先检查是否空闲时间超过这个时间，
     * 如果超过，释放 socket 重新建立（单位：ms）
     */
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

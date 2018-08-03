package com.changhr.cbox.httpclientpro.service;

import org.apache.http.config.Registry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.TimeUnit;

/**
 * 连接池服务
 *
 * @author changhr
 * @create 2018-08-02 17:48
 */
public class PoolingHttpClientConnectionManagerService {

    private int maxTotal;
    private int defaultMaxPerRoute;
    private int timeToLive;
    private int validateAfterInactivity;

    @Autowired
    @Qualifier("socketFactoryRegistry")
    private Registry<ConnectionSocketFactory> socketFactoryRegistry;

    public PoolingHttpClientConnectionManagerService(int maxTotal, int defaultMaxPerRoute, int timeToLive,
                                                     int validateAfterInactivity) {
        this.maxTotal = maxTotal;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
        this.timeToLive = timeToLive;
        this.validateAfterInactivity = validateAfterInactivity;
    }

    /**
     * 实例化一个连接池管理器，设置最大连接数、并发连接数
     * @return
     */
    public PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager(){
        PoolingHttpClientConnectionManager httpClientConnectionManager =
                new PoolingHttpClientConnectionManager(socketFactoryRegistry, null, null, null,
                        timeToLive, TimeUnit.SECONDS);
        //最大连接数
        httpClientConnectionManager.setMaxTotal(maxTotal);
        //并发数
        httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        //定义不活动的时间段（单位：ms），之后必须在租用给使用者之前重新验证持久连接。
        //传递给此方法的非正值会禁用连接验证。
        //此检查有助于检测已经变为陈旧（半关闭）的连接，同时在池中保持不活动状态。
        httpClientConnectionManager.setValidateAfterInactivity(validateAfterInactivity);

        return httpClientConnectionManager;
    }

}

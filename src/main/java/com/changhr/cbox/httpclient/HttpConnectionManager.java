package com.changhr.cbox.httpclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author changhr
 * @create 2020-04-30 14:48
 */
public class HttpConnectionManager {

    private static final Logger log = LoggerFactory.getLogger(HttpConnectionManager.class);

    private CloseableHttpClient httpClient;

    private IdleConnectionMonitorThread monitorThread;

    private RequestConfig requestConfig;

    private PoolingHttpClientConnectionManager manager;

    public HttpConnectionManager(RequestConfig requestConfig, PoolingHttpClientConnectionManager manager) {
        this.requestConfig = requestConfig;
        this.manager = manager;

        monitorThread = new IdleConnectionMonitorThread(this.manager);
        monitorThread.setDaemon(true);
        monitorThread.start();

        this.httpClient = HttpClients.custom().setConnectionManager(this.manager).build();
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    public PoolingHttpClientConnectionManager getManager() {
        return manager;
    }

    public void setManager(PoolingHttpClientConnectionManager manager) {
        this.manager = manager;
    }

    /**
     * 关闭连接池
     * 一般不需要主动调用此接口去关闭连接池
     */
    public void close() {
        if (this.httpClient != null) {
            try {
                this.httpClient.close();
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        if (this.monitorThread != null) {
            this.monitorThread.shutdown();
        }
        if (log.isInfoEnabled()) {
            log.info(HttpConnectionManager.class.getName() + " closed!");
        }
    }
}

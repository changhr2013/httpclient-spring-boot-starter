package com.changhr.cbox.httpclient.properties;

/**
 * @author changhr
 * @create 2020-04-30 11:56
 */
public class RequestProp {

    /** 连接上服务器(握手成功)的时间，超出该时间抛出connect timeout（单位：ms） */
    private int connectTimeout = 1000;

    /**
     * 从连接池中获取到连接的最长时间（单位：ms），超过该时间未拿到可用连接，
     * 会抛出 org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
     * */
    private int connectionRequestTimeout = 500;

    /** 服务器返回数据(response)的时间，超过该时间抛出 read timeout（单位：ms） */
    private int socketTimeout = 10000;

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}

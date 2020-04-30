package com.changhr.cbox.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 此线程用来监控连接池中的空闲连接，执行驱逐策略
 *
 * @author changhr
 */
public class IdleConnectionMonitorThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(IdleConnectionMonitorThread.class);

    private final HttpClientConnectionManager connMgr;

    private volatile boolean shutdown;

    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
        super();
        this.connMgr = connMgr;
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    // Close expired connections
                    connMgr.closeExpiredConnections();
                    // Optionally, close connections
                    // that have been idle longer than 30 sec
                    // connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}

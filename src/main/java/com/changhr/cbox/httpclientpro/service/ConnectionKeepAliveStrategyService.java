package com.changhr.cbox.httpclientpro.service;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

/**
 * 连接保持策略服务
 *
 * @author changhr
 * @create 2018-08-03 9:43
 */
public class ConnectionKeepAliveStrategyService {

    private int keepAliveTime;

    public ConnectionKeepAliveStrategyService(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy(){
        return new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
                // Honor 'keep-alive' header
                HeaderElementIterator iterator = new BasicHeaderElementIterator(
                        httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while(iterator.hasNext()){
                    HeaderElement headerElement = iterator.nextElement();
                    String param = headerElement.getName();
                    String value = headerElement.getValue();
                    if(value != null && param.equalsIgnoreCase("timeout")){
                        try{
                            return Long.parseLong(value) * 1000;
                        }catch (NumberFormatException ignore){

                        }
                    }
                }
                return keepAliveTime * 1000;
            }
        };
    }
}

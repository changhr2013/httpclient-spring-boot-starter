package com.changhr.cbox.httpclient.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author changhr2013
 * @date 2020/5/1
 */
public class BytesResponseHandler extends AbstractResponseHandler<byte[]> {

    public static final BytesResponseHandler INSTANCE = new BytesResponseHandler();

    @Override
    public byte[] handleEntity(HttpEntity entity) throws IOException {
        return EntityUtils.toByteArray(entity);
    }

    @Override
    public byte[] handleResponse(HttpResponse response) throws HttpResponseException, IOException {
        return super.handleResponse(response);
    }
}

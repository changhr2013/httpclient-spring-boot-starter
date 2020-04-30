package com.changhr.cbox.httpclient.http;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author changhr2013
 * @date 2020/5/1
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public class StringResponseHandler extends AbstractResponseHandler<String> {

    public static final StringResponseHandler INSTANCE = new StringResponseHandler();

    /**
     * Returns the entity as a body as a String.
     */
    @Override
    public String handleEntity(final HttpEntity entity) throws IOException {
        return EntityUtils.toString(entity, Charsets.UTF_8);
    }

    @Override
    public String handleResponse(final HttpResponse response) throws HttpResponseException, IOException {
        return super.handleResponse(response);
    }

}

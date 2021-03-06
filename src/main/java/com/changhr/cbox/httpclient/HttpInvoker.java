package com.changhr.cbox.httpclient;

import com.changhr.cbox.httpclient.http.BytesResponseHandler;
import com.changhr.cbox.httpclient.http.StringResponseHandler;
import com.changhr.cbox.httpclient.utils.CollectionUtil;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 负责发送 HTTP 请求的工具类
 *
 * @author changhr2013
 */
@SuppressWarnings({"Duplicates", "unused"})
public class HttpInvoker {

    private static final Logger log = LoggerFactory.getLogger(HttpInvoker.class);

    private static final ContentType TEXT_PLAIN_UTF8 = ContentType.create("text/plain", Consts.UTF_8);

    private HttpConnectionManager manager;

    public HttpInvoker(HttpConnectionManager manager) {
        this.manager = manager;
    }

    public HttpConnectionManager getManager() {
        return manager;
    }

    public void setManager(HttpConnectionManager manager) {
        this.manager = manager;
    }


    /**
     * 发送 HTTP GET 请求
     *
     * @param url 请求 URL
     * @return String   响应结果
     */
    public String sendGet(String url) {
        return sendGet(url, null, null);
    }

    /**
     * 发送 HTTP GET 请求
     *
     * @param url      请求 URL
     * @param paramMap 请求参数
     * @return String   响应结果
     */
    public String sendGet(String url, Map<String, String> paramMap) {
        return sendGet(url, paramMap, null);
    }

    /**
     * 发送 HTTP GET 请求，带自定义 Header
     *
     * @param url       请求 URL
     * @param paramMap  请求参数
     * @param headerMap 自定义 Header
     * @return String   响应结果
     */
    public String sendGet(String url, Map<String, String> paramMap, Map<String, String> headerMap) {


        // 初始化 HttpGet
        HttpGet get = new HttpGet();

        // 字符串参数
        List<NameValuePair> params = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(paramMap)) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        try {
            URI uri = new URIBuilder(url).addParameters(params).build();
            get.setURI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException("请求的 url[" + url + "] 格式错误！", e);
        }

        // 设置 RequestConfig
        get.setConfig(manager.getRequestConfig());

        // 设置自定义 Header
        if (CollectionUtil.isNotEmpty(headerMap)) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                get.setHeader(entry.getKey(), entry.getValue());
            }
        }

        // 执行请求并处理结果
        CloseableHttpClient client = manager.getHttpClient();
        try (CloseableHttpResponse response = client.execute(get)) {
            return StringResponseHandler.INSTANCE.handleResponse(response);
        } catch (IOException e) {
            throw new RuntimeException("请求[" + url + "]发生异常，请检查连接、参数是否正确", e);
        } finally {
            get.releaseConnection();
        }
    }

    /**
     * 发送 HTTP GET 请求，带自定义 Header
     *
     * @param url       请求 URL
     * @param paramMap  请求参数
     * @param headerMap 自定义 Header
     * @return byte[]   响应结果
     */
    public byte[] sendGetToBytes(String url, Map<String, String> paramMap, Map<String, String> headerMap) {

        HttpGet get = new HttpGet();

        // 字符串参数
        List<NameValuePair> params = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(paramMap)) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        // 构建 URI
        try {
            URI uri = new URIBuilder(url).addParameters(params).build();
            get.setURI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException("请求的 url[" + url + "] 格式错误！", e);
        }

        get.setConfig(manager.getRequestConfig());

        // 设置自定义 Header
        if (CollectionUtil.isNotEmpty(headerMap)) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                get.setHeader(entry.getKey(), entry.getValue());
            }
        }

        // 执行请求
        CloseableHttpClient client = manager.getHttpClient();
        try (CloseableHttpResponse response = client.execute(get)) {
            return BytesResponseHandler.INSTANCE.handleResponse(response);
        } catch (IOException e) {
            throw new RuntimeException("请求[" + url + "]发生异常，请检查连接、参数是否正确", e);
        } finally {
            get.releaseConnection();
        }
    }

    /**
     * 发送 HTTP POST 请求
     *
     * @param url      请求 URL
     * @param paramMap 请求参数
     * @return String   响应结果
     */
    public String sendPost(String url, Map<String, String> paramMap) {
        return sendPost(url, paramMap, null);
    }

    /**
     * 发送 HTTP POST 请求，带自定义 Header
     *
     * @param url       请求 URL
     * @param paramMap  请求参数
     * @param headerMap header 信息
     * @return String   响应结果
     */
    public String sendPost(String url, Map<String, String> paramMap, Map<String, String> headerMap) {

        HttpPost post = new HttpPost(url);

        post.setConfig(manager.getRequestConfig());

        // 字符串参数
        List<NameValuePair> params = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(paramMap)) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(params, Consts.UTF_8);
        post.setEntity(requestEntity);

        // 设置自定义 Header
        if (CollectionUtil.isNotEmpty(headerMap)) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                post.setHeader(entry.getKey(), entry.getValue());
            }
        }

        // 执行请求
        CloseableHttpClient client = manager.getHttpClient();
        try (CloseableHttpResponse response = client.execute(post)) {
            return StringResponseHandler.INSTANCE.handleResponse(response);
        } catch (Exception e) {
            throw new RuntimeException("请求[" + url + "]发生异常，请检查连接、参数是否正确", e);
        } finally {
            post.releaseConnection();
            try {
                EntityUtils.consume(requestEntity);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 发送 HTTP POST 请求，带附件以 Part 方式
     *
     * @param url      请求地址
     * @param paramMap 字符串参数 map
     * @param partMap  字节型参数 map
     * @return String    响应结果
     */
    public String sendPostWithPart(String url, Map<String, String> paramMap, Map<String, byte[]> partMap) {
        return sendPostWithPart(url, paramMap, partMap, null);
    }

    /**
     * 发送 HTTP POST 请求，带附件以 Part 方式，带自定义 Header
     *
     * @param url       请求地址
     * @param paramMap  字符串参数 map
     * @param partMap   字节型参数 map
     * @param headerMap header 信息
     * @return String    响应结果
     */
    public String sendPostWithPart(String url, Map<String, String> paramMap,
                                   Map<String, byte[]> partMap, Map<String, String> headerMap) {
        HttpPost post = new HttpPost(url);

        post.setConfig(manager.getRequestConfig());

        // 设置自定义 Header
        if (CollectionUtil.isNotEmpty(headerMap)) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                post.setHeader(entry.getKey(), entry.getValue());
            }
        }

        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        // 字符串参数
        if (CollectionUtil.isNotEmpty(paramMap)) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                entityBuilder.addPart(entry.getKey(), new StringBody(entry.getValue(), TEXT_PLAIN_UTF8));
            }
        }
        // 字节型参数
        if (CollectionUtil.isNotEmpty(partMap)) {
            for (Map.Entry<String, byte[]> entry : partMap.entrySet()) {
                entityBuilder.addPart(entry.getKey(), new ByteArrayBody(entry.getValue(), entry.getKey()));
            }
        }
        HttpEntity requestEntity = entityBuilder.build();
        post.setEntity(requestEntity);

        // 执行请求
        CloseableHttpClient client = manager.getHttpClient();
        try (CloseableHttpResponse response = client.execute(post)) {
            return StringResponseHandler.INSTANCE.handleResponse(response);
        } catch (Exception e) {
            throw new RuntimeException("请求[" + url + "]发生异常，请检查连接、参数是否正确", e);
        } finally {
            post.releaseConnection();
            if (requestEntity != null) {
                try {
                    EntityUtils.consume(requestEntity);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 发送 HTTP POST 请求，带字节 Body
     *
     * @param url  请求的远程服务地址
     * @param data 字节数据
     * @return String 远程服务器响应结果
     */
    public String sendPost(String url, byte[] data) {
        return sendPost(url, data, null);
    }

    /**
     * 发送 HTTP POST 请求，带字节 Body，带自定义 Header
     *
     * @param url       请求的远程服务地址
     * @param data      字节数据
     * @param headerMap 自定义请求头
     * @return String 远程服务器响应结果
     */
    public String sendPost(String url, byte[] data, Map<String, String> headerMap) {

        HttpPost post = new HttpPost(url);

        post.setConfig(manager.getRequestConfig());

        // 设置自定义 Header
        if (CollectionUtil.isNotEmpty(headerMap)) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // 设置 byte[] 请求体
        ByteArrayEntity requestEntity = new ByteArrayEntity(data);
        post.setEntity(requestEntity);

        // 执行请求
        CloseableHttpClient client = manager.getHttpClient();
        try (CloseableHttpResponse response = client.execute(post)) {
            return StringResponseHandler.INSTANCE.handleResponse(response);
        } catch (Exception e) {
            throw new RuntimeException("请求[" + url + "]发生异常，请检查连接、参数是否正确", e);
        } finally {
            post.releaseConnection();
            try {
                EntityUtils.consume(requestEntity);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 发送 HTTP POST 请求，Content-Type = application/json
     *
     * @param url         请求的远程服务地址
     * @param requestJson json 请求体
     * @return String 远程服务器响应结果
     */
    public String sendPost(String url, String requestJson) {
        return sendPost(url, requestJson, null);
    }

    /**
     * 发送 HTTP POST 请求，Content-Type = application/json，带自定义 Header
     *
     * @param url         远程服务地址
     * @param requestJson json 请求体
     * @param headerMap   自定义请求头
     * @return String 远程服务器响应结果
     */
    public String sendPost(String url, String requestJson, Map<String, String> headerMap) {

        HttpPost post = new HttpPost(url);

        post.setConfig(manager.getRequestConfig());

        // 设置请求类型为 application/json
        post.setHeader(MIME.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

        // 设置自定义 Header
        if (CollectionUtil.isNotEmpty(headerMap)) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // 设置 Body
        StringEntity requestEntity = new StringEntity(requestJson, Consts.UTF_8);
        post.setEntity(requestEntity);

        // 执行请求
        CloseableHttpClient client = manager.getHttpClient();
        try (CloseableHttpResponse response = client.execute(post)) {
            return StringResponseHandler.INSTANCE.handleResponse(response);
        } catch (IOException e) {
            throw new RuntimeException("请求[" + url + "]发生异常，请检查连接、参数是否正确", e);
        } finally {
            post.releaseConnection();
            try {
                EntityUtils.consume(requestEntity);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
package com.changhr.cbox.httpclient;

import com.changhr.cbox.httpclient.utils.CollectionUtil;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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

    private static final Logger log = LoggerFactory.getLogger(HttpInvoker.class);

    private static final ContentType TEXT_PLAIN_UTF8 = ContentType.create("text/plain", Consts.UTF_8);


    /**
     * 发送 HTTP GET 请求
     *
     * @param url      请求 URL
     * @return String   响应结果
     * @throws Exception 异常
     */
    public String sendGet(String url) throws Exception {
        return sendGet(url, null, null);
    }

    /**
     * 发送 HTTP GET 请求
     *
     * @param url      请求 URL
     * @param paramMap 请求参数
     * @return String   响应结果
     * @throws Exception 异常
     */
    public String sendGet(String url, Map<String, String> paramMap) throws Exception {
        return sendGet(url, paramMap, null);
    }

    /**
     * 发送 HTTP GET 请求，带自定义 Header
     *
     * @param url       请求 URL
     * @param paramMap  请求参数
     * @param headerMap 自定义 Header
     * @return String   响应结果
     * @throws Exception 异常
     */
    public String sendGet(String url,
                          Map<String, String> paramMap,
                          Map<String, String> headerMap) throws Exception {

        CloseableHttpClient client = manager.getHttpClient();
        CloseableHttpResponse response = null;

        try {
            List<NameValuePair> params = new ArrayList<>();
            // 字符串参数
            if (CollectionUtil.isNotEmpty(paramMap)) {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            String urlParameter = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));

            HttpGet get = new HttpGet(url + "?" + urlParameter);

            get.setConfig(manager.getRequestConfig());

            // 设置自定义 Header
            if (CollectionUtil.isNotEmpty(headerMap)) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    get.setHeader(entry.getKey(), entry.getValue());
                }
            }

            // 执行请求
            response = client.execute(get);
            HttpEntity entity = response.getEntity();

            // 验证响应状态
            int code = response.getStatusLine().getStatusCode();
            if (code < HttpStatus.SC_OK || code >= HttpStatus.SC_BAD_REQUEST) {
                // 消费掉内容
                EntityUtils.consume(entity);
                throw new Exception("HTTP Status Code=[" + code + "]");
            }

            // 返回响应内容
            return EntityUtils.toString(entity, Consts.UTF_8);
        } catch (Exception e) {
            throw new Exception("请求[" + url + "]发生异常，请检查连接、参数是否正确", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
        }
    }

    /**
     * 发送 HTTP GET 请求，带自定义 Header
     *
     * @param url       请求 URL
     * @param paramMap  请求参数
     * @param headerMap 自定义 Header
     * @return byte[]   响应结果
     * @throws Exception 异常
     */
    public byte[] sendGetToBytes(String url,
                                 Map<String, String> paramMap,
                                 Map<String, String> headerMap) throws Exception {

        CloseableHttpClient client = manager.getHttpClient();
        CloseableHttpResponse response = null;

        try {
            List<NameValuePair> params = new ArrayList<>();
            // 字符串参数
            if (CollectionUtil.isNotEmpty(paramMap)) {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            String urlParameter = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));

            HttpGet get = new HttpGet(url + "?" + urlParameter);

            get.setConfig(manager.getRequestConfig());

            // 设置自定义 Header
            if (CollectionUtil.isNotEmpty(headerMap)) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    get.setHeader(entry.getKey(), entry.getValue());
                }
            }

            // 执行请求
            response = client.execute(get);
            HttpEntity entity = response.getEntity();

            // 验证响应状态
            int code = response.getStatusLine().getStatusCode();
            if (code < HttpStatus.SC_OK || code >= HttpStatus.SC_BAD_REQUEST) {
                // 消费掉内容
                EntityUtils.consume(entity);
                throw new Exception("HTTP Status Code=[" + code + "]");
            }

            // 返回响应内容
            return EntityUtils.toByteArray(entity);
        } catch (Exception e) {
            throw new Exception("请求[" + url + "]发生异常，请检查连接、参数是否正确", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
        }
    }

    /**
     * 发送 HTTP POST 请求
     *
     * @param url      请求 URL
     * @param paramMap 请求参数
     * @return String   响应结果
     * @throws Exception 异常
     */
    public String sendPost(String url, Map<String, String> paramMap) throws Exception {
        return sendPost(url, paramMap, null);
    }

    /**
     * 发送 HTTP POST 请求，带自定义 Header
     *
     * @param url       请求 URL
     * @param paramMap  请求参数
     * @param headerMap header 信息
     * @return String   响应结果
     * @throws Exception 异常
     */
    public String sendPost(String url,
                           Map<String, String> paramMap,
                           Map<String, String> headerMap) throws Exception {

        CloseableHttpClient client = manager.getHttpClient();

        HttpPost post = new HttpPost(url);
        post.setConfig(manager.getRequestConfig());

        // 设置自定义 Header
        if (CollectionUtil.isNotEmpty(headerMap)) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                post.setHeader(entry.getKey(), entry.getValue());
            }
        }

        List<NameValuePair> params = new ArrayList<>();
        // 字符串参数
        if (CollectionUtil.isNotEmpty(paramMap)) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        post.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));

        // 执行请求
        try (CloseableHttpResponse response = client.execute(post)) {

            HttpEntity entity = response.getEntity();

            // 验证响应状态
            int code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.SC_OK) {
                // 消费掉内容
                EntityUtils.consume(entity);
                throw new Exception("HTTP Status Code=[" + code + "]");
            }

            // 返回响应内容
            return EntityUtils.toString(entity, Consts.UTF_8);
        } catch (Exception e) {
            throw new Exception("请求[" + url + "]发生异常，请检查连接、参数是否正确", e);
        }
    }

    /**
     * 发送 HTTP POST 请求，带附件以 Part 方式
     *
     * @param url      请求地址
     * @param paramMap 字符串参数 map
     * @param partMap  字节型参数 map
     * @return String    响应结果
     * @throws Exception               异常
     * @throws IOException             I/O 异常
     * @throws ClientProtocolException HTTP 协议异常
     */
    public String sendPostWithPart(String url,
                                   Map<String, String> paramMap,
                                   Map<String, byte[]> partMap) throws Exception {
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
     * @throws Exception               异常
     * @throws IOException             I/O 异常
     * @throws ClientProtocolException HTTP 协议异常
     */
    public String sendPostWithPart(String url,
                                   Map<String, String> paramMap,
                                   Map<String, byte[]> partMap,
                                   Map<String, String> headerMap) throws Exception {

        CloseableHttpClient client = manager.getHttpClient();
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
        try (CloseableHttpResponse response = client.execute(post)) {
            HttpEntity entity = response.getEntity();

            // 验证响应状态
            int code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.SC_OK) {
                // 消费掉内容
                EntityUtils.consume(entity);
                throw new Exception("HTTP Status Code=[" + code + "]");
            }

            // 返回响应内容
            return EntityUtils.toString(entity, Consts.UTF_8);
        } catch (Exception e) {
            throw new Exception("请求[" + url + "]发生异常，请检查连接、参数是否正确", e);
        } finally {
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
    public String sendPost(String url, byte[] data) throws Exception {
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
    public String sendPost(String url, byte[] data, Map<String, String> headerMap)
            throws Exception {

        CloseableHttpClient client = manager.getHttpClient();

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
        try (CloseableHttpResponse response = client.execute(post)) {
            HttpEntity entity = response.getEntity();

            // 验证响应状态
            int code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.SC_OK) {
                EntityUtils.consume(entity);
                throw new Exception("HTTP Status Code=[" + code + "]");
            }

            // 返回响应内容
            return EntityUtils.toString(entity, Consts.UTF_8);
        } catch (Exception e) {
            throw new Exception("请求[" + url + "]发生异常，请检查连接、参数是否正确", e);
        }
    }

    /**
     * 发送 HTTP POST 请求，Content-Type = application/json
     *
     * @param url         请求的远程服务地址
     * @param requestJson json 请求体
     * @return String 远程服务器响应结果
     * @throws Exception 异常
     */
    public String sendPost(String url, String requestJson) throws Exception {
        return sendPost(url, requestJson, null);
    }

    /**
     * 发送 HTTP POST 请求，Content-Type = application/json，带自定义 Header
     *
     * @param url         远程服务地址
     * @param requestJson json 请求体
     * @param headerMap   自定义请求头
     * @return String 远程服务器响应结果
     * @throws Exception 异常
     */
    public String sendPost(String url,
                           String requestJson,
                           Map<String, String> headerMap) throws Exception {

        CloseableHttpClient client = manager.getHttpClient();

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
        post.setEntity(new StringEntity(requestJson, Consts.UTF_8));

        // 执行请求
        try (CloseableHttpResponse response = client.execute(post)) {
            HttpEntity entity = response.getEntity();

            // 验证响应状态
            int code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.SC_OK) {
                EntityUtils.consume(entity);
                throw new Exception("HTTP Status Code=[" + code + "]");
            }

            // 返回响应内容
            return EntityUtils.toString(entity, Consts.UTF_8);
        } catch (Exception e) {
            throw new Exception("请求[" + url + "]发生异常，请检查连接、参数是否正确", e);
        }
    }
}
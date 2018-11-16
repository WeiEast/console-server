package com.treefinance.saas.management.console.common.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.saas.management.console.common.domain.dto.HttpResponseResult;
import com.treefinance.saas.management.console.common.exceptions.RequestFailedException;
import com.treefinance.toolkit.util.http.servlet.ServletResponses;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * httpclient 调用工具类
 * Created by yh-treefinance on 2017/5/17.
 */
public class HttpClientUtils {
    // 日志
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    // 连接池
    private static PoolingHttpClientConnectionManager connMgr;
    // 超时时间
    private static final int MAX_TIMEOUT = 3000;

    static {

        //采用绕过验证的方式处理https请求
        SSLContext sslcontext = null;
        try {
            sslcontext = createIgnoreVerifySSL();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();

        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // 设置连接池大小
        connMgr.setMaxTotal(500);
        connMgr.setDefaultMaxPerRoute(500);


    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    /**
     * 获取默认配置
     *
     * @return
     */
    private static RequestConfig getDefaultConfig() {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.BEST_MATCH)
                .setExpectContinueEnabled(true)
                .setStaleConnectionCheckEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .build();
        return defaultRequestConfig;
    }

    /**
     * 获取默认配置
     *
     * @return
     */
    private static RequestConfig getBaseConfig() {
        RequestConfig defaultRequestConfig = getDefaultConfig();
        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
                .setSocketTimeout(MAX_TIMEOUT)
                .setConnectTimeout(MAX_TIMEOUT)
                .setConnectionRequestTimeout(MAX_TIMEOUT)
                .build();
        return requestConfig;
    }

    /**
     * 获取默认配置
     *
     * @param timeOut 超时时间
     * @return
     */
    private static RequestConfig getConfigWithTimeOut(int timeOut) {
        RequestConfig defaultRequestConfig = getDefaultConfig();
        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
                .setSocketTimeout(timeOut)
                .setConnectTimeout(timeOut)
                .setConnectionRequestTimeout(timeOut)
                .build();
        return requestConfig;
    }

    /**
     * 获取默认client
     *
     * @return
     */
    private static CloseableHttpClient getClient() {
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connMgr).setRetryHandler((e, n, c) -> {
            return false;
        }).build();
        return httpclient;
    }

    /**
     * 获取重试默认client
     *
     * @param retryTimes
     * @return
     */
    private static CloseableHttpClient getRetryClient(String url, Byte retryTimes) {

        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connMgr).setRetryHandler((e, n, c) -> {
            logger.info("request {} failed : error={}, retry {} （max {} times）...", url, e.getMessage(), n, retryTimes);
            if (retryTimes != null && retryTimes.intValue() > n) {
                return true;
            }
            return false;
        }).build();
        return httpclient;
    }

    /**
     * 发送 GET 请求（HTTP），不带输入数据
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, new HashMap<String, Object>());
    }

    public static String doGetWithHeaders(String url, Map<String, String> headers) {
        return doGetWithHeaders(url, new HashMap<>(), headers);
    }

    public static String doGetWithHeaders(String url, HashMap<String, Object> params, Map<String, String> headers) {
        long start = System.currentTimeMillis();

        List<String> paramList = Lists.newArrayList();
        for (String key : params.keySet()) {
            paramList.add(key + "=" + params.get(key));
        }
        String apiUrl = url + (url.contains("?") ? "&" : "?") + Joiner.on("&").join(paramList);
        String result = null;
        CloseableHttpClient httpclient = getClient();
        CloseableHttpResponse response = null;
        int statusCode = 0;
        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            httpGet.setConfig(getBaseConfig());

            List<Header> headerList = Lists.newArrayList();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                Header header = new BasicHeader(entry.getKey(), entry.getValue());
                headerList.add(header);
            }
            Header[] headerArray = new Header[headerList.size()];
            headerArray = headerList.toArray(headerArray);
            httpGet.setHeaders(headerArray);

            response = httpclient.execute(httpGet);
            statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, "UTF-8");
            }
            if (statusCode != HttpStatus.SC_OK) {
                throw new RequestFailedException(apiUrl, statusCode, result);
            }
        } catch (IOException e) {
            throw new RequestFailedException(apiUrl, statusCode, result, e);
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info(" doGet completed: url={}, params={}, statusCode={} , result={} , cost {} ms ",
                        url, JSON.toJSONString(params), statusCode, result, (System.currentTimeMillis() - start));
            }
            closeResponse(response);
        }
        return result;
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> params) {
        long start = System.currentTimeMillis();

        List<String> paramList = Lists.newArrayList();
        for (String key : params.keySet()) {
            paramList.add(key + "=" + params.get(key));
        }
        String apiUrl = url + (url.contains("?") ? "&" : "?") + Joiner.on("&").join(paramList);
        String result = null;
        CloseableHttpClient httpclient = getClient();
        CloseableHttpResponse response = null;
        int statusCode = 0;
        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            httpGet.setConfig(getBaseConfig());
            response = httpclient.execute(httpGet);
            statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, "UTF-8");
            }
            if (statusCode != HttpStatus.SC_OK) {
                throw new RequestFailedException(apiUrl, statusCode, result);
            }
        } catch (IOException e) {
            throw new RequestFailedException(apiUrl, statusCode, result, e);
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info(" doGet completed: url={}, params={}, statusCode={} , result={} , cost {} ms ",
                        url, JSON.toJSONString(params), statusCode, result, (System.currentTimeMillis() - start));
            }
            closeResponse(response);
        }
        return result;
    }


    /**
     * 发送 GET 请求（HTTP），K-V形式
     *
     * @param url
     * @param params
     * @return
     */
    public static HttpResponseResult doGetResult(String url, Map<String, Object> params) {
        long start = System.currentTimeMillis();

        List<String> paramList = Lists.newArrayList();
        for (String key : params.keySet()) {
            paramList.add(key + "=" + params.get(key));
        }
        String apiUrl = url + (url.contains("?") ? "&" : "?") + Joiner.on("&").join(paramList);
        HttpResponseResult result = new HttpResponseResult();
        String resultBody = null;
        CloseableHttpClient httpclient = getClient();
        CloseableHttpResponse response = null;
        int statusCode = 0;
        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            httpGet.setConfig(getBaseConfig());
            response = httpclient.execute(httpGet);
            statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                resultBody = IOUtils.toString(instream, "UTF-8");
            }
            result.setResponseBody(resultBody);
            result.setStatusCode(statusCode);
        } catch (IOException e) {
            throw new RequestFailedException(apiUrl, statusCode, resultBody, e);
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info(" doGet completed: url={}, params={}, statusCode={} , result={} , cost {} ms ",
                        url, JSON.toJSONString(params), statusCode, result, (System.currentTimeMillis() - start));
            }
            closeResponse(response);
        }
        return result;
    }


    /**
     * 发送 GET 请求（HTTP），K-V形式
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGetWithTimoutAndRetryTimes(String url, Byte timeOut, Byte retryTimes, Map<String, Object> params) {
        long start = System.currentTimeMillis();

        List<String> paramList = Lists.newArrayList();
        for (String key : params.keySet()) {
            paramList.add(key + "=" + params.get(key));
        }
        String apiUrl = url + (url.contains("?") ? "&" : "?") + Joiner.on("&").join(paramList);
        String result = "";
        CloseableHttpClient httpclient = getRetryClient(apiUrl, retryTimes);
        CloseableHttpResponse response = null;
        int statusCode = 0;
        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            httpGet.setConfig(getConfigWithTimeOut(timeOut * 1000));
            response = httpclient.execute(httpGet);
            statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, "UTF-8");
            }
            if (statusCode != HttpStatus.SC_OK) {
                throw new RequestFailedException(url, statusCode, result);
            }
        } catch (IOException e) {
            logger.error("doGet exception:url={}, params={}, statusCode={} , result={}",
                    apiUrl, JSON.toJSONString(params), statusCode, result, e);
            throw new RequestFailedException(url, statusCode, result, e);
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info(" doGet completed: url={}, params={}, statusCode={} , result={} , cost {} ms ",
                        apiUrl, JSON.toJSONString(params), statusCode, result, (System.currentTimeMillis() - start));
            }
            closeResponse(response);
        }
        return result;
    }

    /**
     * 发送 POST 请求（HTTP）
     *
     * @param url
     * @return
     */
    public static String doPost(String url) {
        return doPost(url, new HashMap<String, Object>());
    }


    /**
     * 发送 POST 请求（HTTP）
     *
     * @param url
     * @param timeOut    秒
     * @param retryTimes 重试次数
     * @param params     参数
     * @return
     */
    public static String doPostWithTimoutAndRetryTimes(String url, Byte timeOut, Byte retryTimes, Map<String, Object> params) {
        long start = System.currentTimeMillis();
        CloseableHttpClient httpClient = getRetryClient(url, retryTimes);
        String result = "";
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;

        int statusCode = 0;
        StringBuffer paramsBf = new StringBuffer();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            paramsBf.append(entry.getKey()).append("=").append(entry.getValue());
        }
        try {
            httpPost.setConfig(getConfigWithTimeOut(timeOut * 1000));
//            List<NameValuePair> pairList = new ArrayList<>(params.size());
//            for (Map.Entry<String, Object> entry : params.entrySet()) {
//                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
//                        .getValue().toString());
//                pairList.add(pair);
//            }
//            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));


            StringEntity stringEntity = new StringEntity(paramsBf.toString(), "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");

            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);

            statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
            if (statusCode != HttpStatus.SC_OK) {
                throw new RequestFailedException(url, statusCode, result);
            }
        } catch (IOException e) {
            logger.error("doPost exception: url={}, statusCode={} ,result={}", url, statusCode, result, e);
            throw new RequestFailedException(url, statusCode, result, e);
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info(" doPost completed: url={}, statusCode={} ,result={}, cost {} ms ",
                        url, statusCode, result, (System.currentTimeMillis() - start));
            }
            closeResponse(response);
        }
        return result;

    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param url
     * @param params 参数map
     * @return
     */
    public static String doPost(String url, Map<String, Object> params) {
        long start = System.currentTimeMillis();
        CloseableHttpClient httpClient = getClient();
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;

        int statusCode = 0;
        try {
            httpPost.setConfig(getBaseConfig());
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);

            statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
            if (statusCode != HttpStatus.SC_OK) {
                throw new RequestFailedException(url, statusCode, result);
            }
        } catch (IOException e) {
            throw new RequestFailedException(url, statusCode, result, e);
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info(" doPost completed: url={}, params={}, statusCode={} ,result={}, cost {} ms ",
                        url, JSON.toJSONString(params), statusCode, result, (System.currentTimeMillis() - start));
            }
            closeResponse(response);
        }
        return result;
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param url
     * @param params 参数map
     * @return
     */
    public static String doPostWithHeaders(String url, Map<String, Object> params, Map<String, String> headers) {
        long start = System.currentTimeMillis();
        CloseableHttpClient httpClient = getClient();
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;

        int statusCode = 0;
        try {
            httpPost.setConfig(getBaseConfig());
            String json = JSON.toJSONString(params);
            StringEntity s = new StringEntity(json, ContentType.APPLICATION_JSON);
            s.setContentEncoding("UTF-8");
            List<Header> headerList = Lists.newArrayList();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                Header header = new BasicHeader(entry.getKey(), entry.getValue());
                headerList.add(header);
            }
            httpPost.setEntity(s);
            Header[] headerArray = new Header[headerList.size()];
            headerArray = headerList.toArray(headerArray);
            httpPost.setHeaders(headerArray);
            response = httpClient.execute(httpPost);

            statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
            if (statusCode != HttpStatus.SC_OK) {
                throw new RequestFailedException(url, statusCode, result);
            }
        } catch (IOException e) {
            throw new RequestFailedException(url, statusCode, result, e);
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info(" doPost completed: url={}, params={}, statusCode={} ,result={}, cost {} ms ",
                        url, JSON.toJSONString(params), statusCode, result, (System.currentTimeMillis() - start));
            }
            closeResponse(response);
        }
        return result;
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param url
     * @param json json对象
     * @return
     */
    public static String doPost(String url, Object json) {
        long start = System.currentTimeMillis();
        CloseableHttpClient httpClient = getClient();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;

        int statusCode = 0;
        try {
            httpPost.setConfig(getBaseConfig());
            StringEntity stringEntity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);

            statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                httpStr = EntityUtils.toString(entity, "UTF-8");
            }
            if (statusCode != HttpStatus.SC_OK) {
                throw new RequestFailedException(url, statusCode, httpStr);
            }
        } catch (IOException e) {
            throw new RequestFailedException(url, statusCode, null, e);
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info(" doPost completed: url={}, json={}, statusCode={} ,result={}, cost {} ms ",
                        url, JSON.toJSONString(json), statusCode, httpStr, (System.currentTimeMillis() - start));
            }
            closeResponse(response);
        }
        return httpStr;
    }


    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param url
     * @param json json对象
     * @return
     */
    public static HttpResponseResult doPostResult(String url, Object json) {
        long start = System.currentTimeMillis();
        CloseableHttpClient httpClient = getClient();
        HttpResponseResult result = new HttpResponseResult();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;

        int statusCode = 0;
        try {
            httpPost.setConfig(getBaseConfig());
            StringEntity stringEntity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);

            statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                httpStr = EntityUtils.toString(entity, "UTF-8");
            }
            result.setStatusCode(statusCode);
            result.setResponseBody(httpStr);
        } catch (IOException e) {
            throw new RequestFailedException(url, statusCode, null, e);
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info(" doPost completed: url={}, json={}, statusCode={} ,result={}, cost {} ms ",
                        url, JSON.toJSONString(json), statusCode, httpStr, (System.currentTimeMillis() - start));
            }
            closeResponse(response);
        }
        return result;
    }


    public static void doGetForward(String url, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        long start = System.currentTimeMillis();
        Map<String, Object> paramMap = Maps.newHashMap();
        Enumeration<String> paramNames = httpRequest.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            paramMap.put(paramName, httpRequest.getParameter(paramName));
        }
        List<String> paramList = Lists.newArrayList();
        for (String key : paramMap.keySet()) {
            paramList.add(key + "=" + paramMap.get(key));
        }
        String apiUrl = url + (url.contains("?") ? "&" : "?") + Joiner.on("&").join(paramList);
        CloseableHttpClient httpclient = getClient();
        CloseableHttpResponse response = null;
        int statusCode = 0;
        String responseStr = null;
        String contentType = null;
        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            httpGet.setConfig(getBaseConfig());
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            statusCode = response.getStatusLine().getStatusCode();
            contentType = entity.getContentType().getValue();
            Header[] headers = response.getHeaders("Content-Disposition");
            for (Header header : headers) {
                httpResponse.setHeader(header.getName(), header.getValue());
            }
            if (StringUtils.isNotBlank(contentType) && !StringUtils.equalsIgnoreCase(contentType, ContentType.APPLICATION_JSON.getMimeType())) {
                httpResponse.setContentType(contentType);
                OutputStream outputStream = new BufferedOutputStream(httpResponse.getOutputStream());
                entity.writeTo(outputStream);
                outputStream.flush();
            } else {
                responseStr = EntityUtils.toString(entity, "utf-8");
                ServletResponses.response(httpResponse, statusCode, contentType, responseStr);
            }
        } catch (IOException e) {
            throw new RequestFailedException(apiUrl, statusCode, null, e);
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info(" doGetForward completed: apiUrl={}, statusCode={},contentType={},response={},cost {} ms ",
                        apiUrl, statusCode, contentType, responseStr, System.currentTimeMillis() - start);
            }
            closeResponse(response);
        }
    }


    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param url
     * @param httpRequest
     * @param httpResponse
     */
    public static void doPostForward(String url, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        long start = System.currentTimeMillis();
        CloseableHttpClient httpClient = getClient();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;

        int statusCode = 0;
        String json = null;
        String responseStr = null;
        String contentType = null;
        try {
            httpPost.setConfig(getBaseConfig());
            json = IOUtils.toString(httpRequest.getInputStream(), "UTF-8");
            //解决中文乱码问题
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            statusCode = response.getStatusLine().getStatusCode();
            contentType = entity.getContentType().getValue();
            responseStr = EntityUtils.toString(entity, "utf-8");

            ServletResponses.response(httpResponse, statusCode, contentType, responseStr);

        } catch (IOException e) {
            throw new RequestFailedException(url, statusCode, null, e);
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info(" doPostForward completed: url={}, request={},statusCode={},contentType={},response={},cost {} ms ",
                        url, json, statusCode, contentType, responseStr, System.currentTimeMillis() - start);
            }
            closeResponse(response);
        }

    }


    public static void doPostMutiForward(String url, HttpServletRequest httpRequest,
                                         HttpServletResponse httpResponse, String fieldName,
                                         String fileName, InputStream targetIn) {
        long start = System.currentTimeMillis();
        CloseableHttpResponse response = null;
        int statusCode = 0;
        String responseStr = null;
        String contentType = null;
        try {
            CloseableHttpClient httpClient = getClient();
            HttpPost httpPost = new HttpPost(url);
            //解决中文乱码问题
            StringEntity stringEntity = new StringEntity(IOUtils.toString(httpRequest.getInputStream(), "UTF-8"), ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);

            InputStreamBody bin = new InputStreamBody(targetIn, fileName);
            StringBody uploadFileName = new StringBody(fileName,
                    ContentType.create("text/plain", Consts.UTF_8));

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addPart(fieldName, bin).addPart("fileName", uploadFileName).addPart("fieldName", uploadFileName)
                    .setCharset(CharsetUtils.get("UTF-8")).build();

            httpPost.setEntity(reqEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            statusCode = response.getStatusLine().getStatusCode();
            contentType = entity.getContentType().getValue();
            responseStr = EntityUtils.toString(entity, "utf-8");

            ServletResponses.response(httpResponse, statusCode, contentType, responseStr);
        } catch (IOException e) {
            throw new RequestFailedException(url, statusCode, null, e);
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info(" doPostMutiForward completed: url={}, statusCode={},contentType={},response={},cost {} ms ",
                        url, statusCode, contentType, responseStr, System.currentTimeMillis() - start);
            }
            closeResponse(response);
        }
    }

    /**
     * 关闭响应流
     *
     * @param response
     */
    private static void closeResponse(CloseableHttpResponse response) {
        if (response != null) {
            try {
                EntityUtils.consume(response.getEntity());
            } catch (IOException e) {
                logger.error(" closeResponse failed", e);
            }
        }
    }


    public static String doOptions(String url) {
        long start = System.currentTimeMillis();

        String result = null;
        CloseableHttpClient httpclient = getClient();
        CloseableHttpResponse response = null;
        int statusCode = 0;
        try {
            HttpOptions httpOptions = new HttpOptions(url);
            httpOptions.setConfig(getBaseConfig());
            response = httpclient.execute(httpOptions);
            statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, "UTF-8");
            }
            if (statusCode < 200 || statusCode >= 400) {
                throw new RequestFailedException(url, statusCode, result);
            }
        } catch (IOException e) {
            throw new RequestFailedException(url, statusCode, result, e);
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info(" doOptions completed: url={}, statusCode={} , cost {} ms ", url, statusCode, (System.currentTimeMillis() - start));
            }
            closeResponse(response);
        }
        return result;
    }

}

package com.shiroi.multiple_request_frame.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import java.net.HttpCookie;
import java.util.Map;

public class HuToolHttpUtil {

    /**
     * 发送GET或POST请求并动态设置headers和cookies
     * @param url      请求的URL
     * @param headers  请求头信息
     * @param cookies  cookie信息
     * @param isPost   是否为POST请求
     * @return 响应内容
     */
    public static String request(String url, Map<String, String> headers, Map<String, String> cookies, boolean isPost) {

        HttpRequest request = isPost ? HttpUtil.createPost(url) : HttpUtil.createGet(url);

        //设置header
        if (headers != null) {
            request.addHeaders(headers);
        }

        //设置cookies
        if (cookies != null) {
            for (Map.Entry<String, String> entry : cookies.entrySet()) {
                request.cookie(new HttpCookie(entry.getKey(), entry.getValue()));
            }

        }
        return request.execute().body();
    }

    /**
     * 发送GET请求并动态设置headers和cookies
     * @param url      请求的URL
     * @param headers  请求头信息
     * @param cookies  cookie信息
     * @return 响应内容
     */
    public static String get(String url, Map<String, String> headers, Map<String, String> cookies) {
        return request(url, headers, cookies, false);
    }

    /**
     * 发送POST请求并动态设置headers和cookies
     * @param url      请求的URL
     * @param headers  请求头信息
     * @param cookies  cookie信息
     * @return 响应内容
     */
    public static String post(String url, Map<String, String> headers, Map<String, String> cookies) {
        return request(url, headers, cookies, true);
    }
}

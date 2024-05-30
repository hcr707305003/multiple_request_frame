package com.shiroi.multiple_request_frame.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurlBody {
    private String url;

    private final String method = "get";

    private final Map<String, String> headers = new HashMap<>();

    private final Map<String, String> cookies = new HashMap<>();

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }
}

package com.shiroi.multiple_request_frame.service;

import com.shiroi.multiple_request_frame.bean.CurlBody;

import java.util.concurrent.Future;

public interface AsyncService {
    void executeAsync(CurlBody body);

    void executeAsyncDemo(int i);

    Future<String> executeSync(CurlBody body);

    Future<String> executeSyncDemo(int i);
}

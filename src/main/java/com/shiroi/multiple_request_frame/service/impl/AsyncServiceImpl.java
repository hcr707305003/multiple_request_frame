package com.shiroi.multiple_request_frame.service.impl;

import com.shiroi.multiple_request_frame.bean.CurlBody;
import com.shiroi.multiple_request_frame.service.AsyncService;
import com.shiroi.multiple_request_frame.utils.HuToolHttpUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.Future;

@Service
public class AsyncServiceImpl implements AsyncService {

    @Async("asyncServiceExecutor")
    public void executeAsync(CurlBody body) {
        String result = HuToolHttpUtil.request(body.getUrl(), body.getHeaders(), body.getCookies(), Objects.equals(body.getMethod(), "post"));
        System.out.println("异步回调结果：" + result);
    }

    //异步多线程调用
    @Async("asyncServiceExecutor")
    public void executeAsyncDemo(int i) {
        try{
            System.out.println("开始-执行异步线程：" + i);
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("结束-执行异步线程：" + i);
    }

    @Async("asyncServiceExecutor")
    public Future<String> executeSync(CurlBody body){
        String result = HuToolHttpUtil.request(body.getUrl(), body.getHeaders(), body.getCookies(), Objects.equals(body.getMethod(), "post"));
        System.out.println("同步回调结果：" + result);
        return new AsyncResult<>(result);
    }

    @Async("asyncServiceExecutor")
    public Future<String> executeSyncDemo(int i){
        System.out.println("开始-执行同步线程：" + i);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束-执行同步线程：" + i);
        return new AsyncResult<>("同步线程执行完毕：" + i + "\n");
    }
}

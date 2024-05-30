package com.shiroi.multiple_request_frame.controller;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiroi.multiple_request_frame.bean.CurlBody;
import com.shiroi.multiple_request_frame.service.AsyncService;
import com.shiroi.multiple_request_frame.utils.HuToolHttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private AsyncService asyncService;

    /**
     * 同步多线程调用
     * @param  body
     * @return
     */
    @RequestMapping("async")
    public String asyncHandle(
            @RequestParam(name = "body", required = false, defaultValue = "[]") String body
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 将JSON数组转换为List<MyRequest>
            List<CurlBody> requests = objectMapper.readValue(body, new TypeReference<>(){});
            //并发处理项
            for (CurlBody request : requests) {
                asyncService.executeAsync(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 同步多线程调用
     * @param  body
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("sync")
    public List<String> syncHandle(
            @RequestParam(name = "body", required = false, defaultValue = "[]") String body
    ) throws ExecutionException, InterruptedException {
        List<String> resultList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 将JSON数组转换为List<MyRequest>
            List<CurlBody> requests = objectMapper.readValue(body, new TypeReference<>(){});
            //并发处理项
            for (CurlBody request : requests) {
                Future<String> future = asyncService.executeSync(request);
                resultList.add(future.get());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * 获取头信息
     * @param request
     * @return
     */
    @RequestMapping("header")
    public String header(
            HttpServletRequest request
    ) {
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headerValues = request.getHeaders(headerName);
            while (headerValues.hasMoreElements()) {
                String headerValue = headerValues.nextElement();
                headers.append(headerName).append(": ").append(headerValue).append("\n");
            }
        }
        // 打印结果
        return headers.toString();
    }

    /**
     * 异步多线程（案例）
     * @param num
     * @return
     */
    @RequestMapping("asyncDemo")
    public String asyncDemo(
        @RequestParam(name = "num", required = false, defaultValue = "5") int num
    ) {
        for (int i = 1; i < num; i++) {
            //调用service层的任务
            asyncService.executeAsyncDemo(i);
        }
        return "success";
    }

    /**
     * 同步多线程（案例）
     * @param num
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("syncDemo")
    public String syncDemo(
        @RequestParam(name = "num", required = false, defaultValue = "5") int num
    ) throws ExecutionException, InterruptedException {
        StringBuilder result = new StringBuilder();
        List<Future<String>> futureList = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            Future<String> future = asyncService.executeSyncDemo(i);
            futureList.add(future);
        }
        // 现在您可以通过遍历futureList来访问这些Future实例
        for (Future<String> future : futureList) {
            result.append(future.get());
        }
        return result.toString();
    }
}

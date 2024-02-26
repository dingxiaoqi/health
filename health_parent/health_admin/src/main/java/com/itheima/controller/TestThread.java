package com.itheima.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description TODO
 * @Author dingxq
 * @Date 2023/8/6 11:00
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/test")
@Slf4j
public class TestThread {
    private ExecutorService executorService;

    private Map localCacheManger ;


    public TestThread() {
        executorService = Executors.newCachedThreadPool();
        localCacheManger = new ConcurrentHashMap<String, Object>();
    }

    @RequestMapping("/001")
    public void test001(String value) {

        if (value.equals(localCacheManger.get("flag_"+value))) {
            System.out.println(value+":线程任务执行中...");
            return;
        }

        localCacheManger.put("flag_"+value, value);

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        CompletableFuture<Void> submit1 = CompletableFuture.runAsync(() -> {
            System.out.println("111111111111");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, executorService);
        futures.add(submit1);

        CompletableFuture<Void> submit2 = CompletableFuture.runAsync(() -> {
            System.out.println("222222222");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, executorService);

        futures.add(submit2);
        CompletableFuture<Void> submit3 = CompletableFuture.runAsync(() -> {
            System.out.println("333333333");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, executorService);

        futures.add(submit3);
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        allFutures.whenComplete((result, exception) -> {

            localCacheManger.remove("flag_" + value, value);
        });
    }

}

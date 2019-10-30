package com.company;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.currentThread;

@Slf4j
@Component
class ServiceTaxes {

    @Async("ThreadPool-ServiceDiscount")
    CompletableFuture<Integer> getDiscount() throws InterruptedException {
        log.info("Getting discount. Thread: {}",  threadInfo());
        Thread.sleep(8000);
        return CompletableFuture.completedFuture(10);
    }

    @Async("ThreadPool-ServiceTaxes")
    CompletableFuture<Double> getTaxes() throws InterruptedException {
        log.info("Getting taxes. Thread: {}",  threadInfo());
        Thread.sleep(5000);
        return CompletableFuture.completedFuture(0.1);
    }


    private String threadInfo(){
     return "Name:" + currentThread().getName() + " Id:"  +currentThread().getId();
    }
}

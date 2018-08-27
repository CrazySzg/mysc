package com.example.xzq.demo;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;

public class Listener {

    private final CountDownLatch latch1 = new CountDownLatch(1);

    
    /**
     * 
     * @param foo
     */
    
    @KafkaListener(id = "foo", topics = "annotated1")
    public void listen1(String foo) {
        this.latch1.countDown();
    }

}
package com.sunny.cloud.system.core.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TestJob {


    @XxlJob("test1")
    public void test1() {
        System.out.println(LocalDateTime.now());
    }
}

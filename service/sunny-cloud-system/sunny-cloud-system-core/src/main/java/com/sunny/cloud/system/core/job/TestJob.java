package com.sunny.cloud.system.core.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class TestJob {


    @XxlJob("test1")
    public void test1() {
        System.out.println(LocalDateTime.now());
    }
}

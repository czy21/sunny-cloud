package com.sunny.maven.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication(scanBasePackages = "com.sunny.order.*")
public class MavenApplication {
    public static void main(String[] args) {
        SpringApplication.run(MavenApplication.class, args);
    }
}

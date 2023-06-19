package com.sunny.cloud.system.portal;

import com.sunny.cloud.system.core.mapper.DictMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackageClasses = DictMapper.class)
@SpringBootApplication(scanBasePackages = "com.sunny.cloud.system.*")
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}

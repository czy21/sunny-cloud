package com.sunny.system.portal;

import com.sunny.framework.file.FileAutoConfigure;
import com.sunny.framework.file.repository.FileRepository;
import com.sunny.system.core.mapper.DictMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ImportAutoConfiguration(classes = {FileAutoConfigure.class})
@MapperScan(basePackageClasses = {DictMapper.class, FileRepository.class})
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication(scanBasePackages = "com.sunny.system.*", exclude = UserDetailsServiceAutoConfiguration.class)
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}

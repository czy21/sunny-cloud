package com.sunny.framework.test.config;

import com.sunny.framework.test.mapper.UserMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.MyBatisJdbcConfiguration;

@Configuration
@EnableJdbcRepositories(basePackageClasses = UserMapper.class)
@Import(MyBatisJdbcConfiguration.class)
public class TestConfigure {


}

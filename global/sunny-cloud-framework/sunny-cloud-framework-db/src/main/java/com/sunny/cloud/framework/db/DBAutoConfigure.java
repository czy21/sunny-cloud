package com.sunny.cloud.framework.db;


import com.sunny.cloud.framework.db.datasource.DynamicDataSourceConfigure;
import com.sunny.cloud.framework.db.datasource.DynamicDataSourceProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureBefore(value = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(value = {DynamicDataSourceProperties.class})
@Import({DynamicDataSourceConfigure.class})
public class DBAutoConfigure {
}

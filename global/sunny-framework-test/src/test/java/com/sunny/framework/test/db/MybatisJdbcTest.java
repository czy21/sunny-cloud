package com.sunny.framework.test.db;

import com.sunny.framework.test.mapper.UserMapper;
import com.sunny.framework.test.model.po.UserPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.MyBatisJdbcConfiguration;

@SpringBootTest
@EnableJdbcRepositories(basePackageClasses = UserMapper.class)
@Import(MyBatisJdbcConfiguration.class)
public class MybatisJdbcTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testInsert() {
        UserPO userPO = new UserPO();
        userPO.setName("你好");
        UserPO inserted = userMapper.save(userPO);
        System.out.println();
    }

//    @Test
//    public void testMybatisSelect() {
//        userMapper.find
//    }
}

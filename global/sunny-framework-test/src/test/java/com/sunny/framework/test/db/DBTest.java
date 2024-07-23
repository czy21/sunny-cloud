package com.sunny.framework.test.db;

import com.sunny.framework.test.mapper.UserMapper;
import com.sunny.framework.test.model.po.UserPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DBTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testMybatisInsert() {
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

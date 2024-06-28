package com.sunny.system.core.service;


import org.jooq.SQL;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

class JooqTest {

    @Test
    public void insertTest() {
        String sql1 = "select :first as a,:second as b,:third as c :third as d";
        SQL a = DSL.sql(sql1);
        System.out.println();

    }

}
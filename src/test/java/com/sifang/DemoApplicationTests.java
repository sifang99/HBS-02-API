package com.sifang;

import com.sifang.service.DeptService;
import com.sifang.service.NumberMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    NumberMessageService numberMessageService;
    @Autowired
    DeptService deptService;
    @Test
    void contextLoads() throws SQLException {
        deptService.getAllDepts();
    }

}

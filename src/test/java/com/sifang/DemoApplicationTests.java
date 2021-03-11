package com.sifang;

import com.sifang.mapper.NumberMessageMapper;
import com.sifang.service.DeptService;
import com.sifang.service.DoctorService;
import com.sifang.service.NumberMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.SQLException;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    NumberMessageService numberMessageService;
    @Autowired
    DeptService deptService;
    @Autowired
    NumberMessageMapper numberMessageMapper;
    @Autowired
    DoctorService doctorService;
    @Test
    void contextLoads() throws SQLException {
        this.numberMessageService.getDoctorsByDeptDate(3, Date.valueOf("2021-03-12"));
    }

}

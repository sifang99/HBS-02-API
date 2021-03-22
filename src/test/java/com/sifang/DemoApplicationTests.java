package com.sifang;

import com.sifang.mapper.NumberMessageMapper;
import com.sifang.mapper.OrderMessageMapper;
import com.sifang.pojo.OrderMessage;
import com.sifang.service.DeptService;
import com.sifang.service.DoctorService;
import com.sifang.service.NumberMessageService;
import com.sifang.service.OrderMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
    @Autowired
    OrderMessageService orderMessageService;
    @Autowired
    OrderMessageMapper orderMessageMapper;
    @Test
    void contextLoads() throws SQLException, ParseException {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setDept(3);
        orderMessage.setOrderDate(Date.valueOf("2021-03-22"));
        orderMessage.setDetailTime("2021-03-25  8:40-8:50");
        orderMessage.setStatus(1);
        orderMessage.setId(25);
        orderMessage.setPatientID("218767839462379845");
        this.orderMessageService.addOrderMessage(orderMessage);
    }

}

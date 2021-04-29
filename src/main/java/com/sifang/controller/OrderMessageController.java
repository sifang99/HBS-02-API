package com.sifang.controller;

import com.sifang.pojo.OrderMessage;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.OrderMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class OrderMessageController {
    @Autowired
    private OrderMessageService orderMessageService;

    //实现分时段就诊预约
    @GetMapping("/getOrderMessage")
    public List<Map<String, String>> getOrderMessage(String doctorNum, String date){
        return orderMessageService.getOrderList(doctorNum, date);
    }

    @PostMapping("/addOrderMessage")
    public ReturnMessage addOrderMessage(@RequestBody OrderMessage orderMessage){
        return this.orderMessageService.addOrderMessage(orderMessage);
    }

    @GetMapping("/getOrderRecord")
    public List<Map<String, String>> getOrderRecord(int userId){
        return this.orderMessageService.getOrderRecord(userId);
    }

    @GetMapping("/returnOrder")
    public ReturnMessage returnMessage(int id){
        return this.orderMessageService.returnNumber(id);
    }

    @GetMapping("/getCheckList")
    public List<Map<String, Object>> getCheckList(){
        return this.orderMessageService.getCheckList();
    }

    @PostMapping("/agreeRetreat")
    public ReturnMessage agreeRetreat(@RequestBody ArrayList<Map<String, Integer>> params){
        return this.orderMessageService.agreeRetreat(params);
    }

    @PostMapping("/disagreeRetreat")
    public ReturnMessage disagreeRetreat(@RequestBody int [] params){
        return this.orderMessageService.disagreeRetreat(params);
    }
}

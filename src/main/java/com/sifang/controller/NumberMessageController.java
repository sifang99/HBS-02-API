package com.sifang.controller;

import com.sifang.pojo.ReturnMessage;
import com.sifang.service.NumberMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class NumberMessageController {
    @Autowired
    private NumberMessageService numberMessageService;

    @PostMapping("/addNumberMessage")
    public ReturnMessage addNumberMessage(@RequestBody Map<String, Object> schedules){
        return this.numberMessageService.addNumberMessage(schedules);
    }
}

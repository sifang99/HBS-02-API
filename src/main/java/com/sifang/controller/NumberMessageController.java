package com.sifang.controller;

import com.sifang.mapper.NumberMessageMapper;
import com.sifang.pojo.NumberMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NumberMessageController {
    @Autowired
    private NumberMessageMapper numberMessageMapper;

    @GetMapping("/getAllNumberMessage")
    public List<NumberMessage> getAllNumberMessage(){
        List<NumberMessage> numberMessageList = numberMessageMapper.getNumerMessageList();
        return numberMessageList;
    }
}

package com.sifang.controller;

import com.sifang.pojo.Attention;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.AttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AttentionController {
    @Autowired
    private AttentionService attentionService;

    @PostMapping("/addAttention")
    public ReturnMessage addAttention(@RequestBody Attention attention){
        return attentionService.addAttention(attention);
    }

    @PostMapping("/updateAttention")
    public ReturnMessage updateAttention(@RequestBody Attention attention){
        return attentionService.updateAttention(attention);
    }

    @GetMapping("/deleteAttention")
    public ReturnMessage deleteAttention(int id){
        return  attentionService.deleteAttention(id);
    }
}

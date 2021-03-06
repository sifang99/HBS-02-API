package com.sifang.controller;

import com.sifang.pojo.Attention;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.AttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
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
    @GetMapping("/getAttentionByDate")
    public List<Attention> getAttentionList(Date date){
        return this.attentionService.getAttentionList(date);
    }

    @GetMapping("/deleteAttention")
    public ReturnMessage deleteAttention(int id[]){
        return  attentionService.deleteAttention(id);
    }

    @GetMapping("/getAttentionList")
    public List<Map<String, Object>> getAttentionList(){
        return attentionService.getAllAttentions();
    }

    @GetMapping("/getAttentionById")
    public Attention getAttentionById(int id){
        return attentionService.getAttentionById(id);
    }
}

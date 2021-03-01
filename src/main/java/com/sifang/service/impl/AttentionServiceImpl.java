package com.sifang.service.impl;

import com.sifang.mapper.AttentionMapper;
import com.sifang.pojo.Attention;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.AttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttentionServiceImpl implements AttentionService {

    @Autowired
    private AttentionMapper attentionMapper;


    @Override
    public ReturnMessage addAttention(Attention attention) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (attentionMapper.addAttention(attention) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("添加成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("添加失败");
        }
        return returnMessage;
    }

    @Override
    public ReturnMessage updateAttention(Attention attention) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (attentionMapper.updateAttention(attention) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("修改成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("修改失败！");
        }
        return returnMessage;
    }

    @Override
    public ReturnMessage deleteAttention(int id) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (attentionMapper.deleteAttention(id) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("删除成功！");
        }else {
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("删除失败!");
        }
        return returnMessage;
    }
}

package com.sifang.service.impl;

import com.sifang.mapper.AttentionMapper;
import com.sifang.pojo.Attention;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.AttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

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
    @Transactional
    public ReturnMessage deleteAttention(int id[]) {
        ReturnMessage returnMessage = new ReturnMessage();
        int length = id.length;
        int count = 0;
        for (int i = 0; i < length; i++){
            if (attentionMapper.deleteAttention(id[i]) >= 1){
                count++;
            }
        }
        if (count == length){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("删除成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("删除失败!");
        }
        return returnMessage;
    }

    @Override
    public List<Attention> getAttentionList(Date publishDay) {
        System.out.println(publishDay);
        return attentionMapper.getAttentionListByDate(publishDay);
    }
}

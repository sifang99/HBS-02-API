package com.sifang.service.impl;

import com.sifang.mapper.CommentMapper;
import com.sifang.pojo.Comment;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;


    @Override
    public ReturnMessage addComment(Comment comment) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (commentMapper.addComment(comment) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("添加成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("添加失败");
        }
        return returnMessage;
    }

    @Override
    public ReturnMessage deleteComment(int id) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (commentMapper.deleteComment(id) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("删除成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("删除失败！");
        }
        return returnMessage;
    }
}

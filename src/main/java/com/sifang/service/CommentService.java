package com.sifang.service;

import com.sifang.pojo.Comment;
import com.sifang.pojo.ReturnMessage;

import java.util.List;

public interface CommentService {
    ReturnMessage addComment(Comment comment);
    ReturnMessage deleteComment(int id);
    List<Comment> getCommentsByDoctorNum(String doctorNum);
}

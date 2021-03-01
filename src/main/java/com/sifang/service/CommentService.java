package com.sifang.service;

import com.sifang.pojo.Comment;
import com.sifang.pojo.ReturnMessage;

public interface CommentService {
    ReturnMessage addComment(Comment comment);
    ReturnMessage deleteComment(int id);
}

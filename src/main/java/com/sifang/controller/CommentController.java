package com.sifang.controller;

import com.sifang.pojo.Comment;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/addComment")
    public ReturnMessage addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("/deleteComment")
    public ReturnMessage deleteComment(int id){
        return commentService.deleteComment(id);
    }
}

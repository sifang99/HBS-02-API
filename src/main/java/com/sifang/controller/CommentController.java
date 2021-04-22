package com.sifang.controller;

import com.sifang.pojo.Comment;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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

    @GetMapping("/getCommentsByDoctorNum")
    public List<Comment> getCommentsByDoctorNum(String doctorNum){
        return this.commentService.getCommentsByDoctorNum(doctorNum);
    }
}

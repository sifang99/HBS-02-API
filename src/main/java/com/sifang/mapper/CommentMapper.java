package com.sifang.mapper;

import com.sifang.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentMapper {
    int addComment(Comment comment);
    int deleteComment(int id);
}

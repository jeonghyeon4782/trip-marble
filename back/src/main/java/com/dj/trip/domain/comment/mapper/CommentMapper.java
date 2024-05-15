package com.dj.trip.domain.comment.mapper;

import com.dj.trip.domain.comment.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    int insertComment(Comment comment);
}

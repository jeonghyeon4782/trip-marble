package com.dj.trip.domain.comment.service;

import com.dj.trip.domain.comment.dto.request.CreateCommentRequest;

public interface CommentService {
    void createComment(CreateCommentRequest createCommentRequest, String memberId);
}

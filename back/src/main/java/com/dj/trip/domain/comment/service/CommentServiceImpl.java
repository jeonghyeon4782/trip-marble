package com.dj.trip.domain.comment.service;


import com.dj.trip.domain.comment.Comment;
import com.dj.trip.domain.comment.dto.request.CreateCommentRequest;
import com.dj.trip.domain.comment.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public void createComment(CreateCommentRequest createCommentRequest, String memberId) {
        Comment comment = Comment.createComment(
                memberId,
                createCommentRequest.reviewId(),
                createCommentRequest.content()
        );

        if (commentMapper.insertComment(comment) == 0) {
            throw new InsufficientAuthenticationException("잘못된 요청");
        }
    }
}

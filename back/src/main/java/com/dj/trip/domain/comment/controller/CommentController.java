package com.dj.trip.domain.comment.controller;

import com.dj.trip.domain.comment.dto.request.CreateCommentRequest;
import com.dj.trip.domain.comment.dto.request.GetCommentsRequest;
import com.dj.trip.domain.comment.dto.request.ModifyCommentRequest;
import com.dj.trip.domain.comment.service.CommentService;
import com.dj.trip.global.dto.ResponseDto;
import com.dj.trip.global.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JWTUtil jwtUtil;

    @PostMapping
    public ResponseDto<?> createComment(@RequestHeader("Authorization") String tokenHeader,
                                        @RequestBody CreateCommentRequest createCommentRequest
    ) {
        String token = tokenHeader.substring(7);
        String memberId = jwtUtil.getMeberId(token);
        commentService.createComment(createCommentRequest, memberId);
        return new ResponseDto<>(HttpStatus.CREATED.value(), "댓글 작성 성공", null);
    }

    @GetMapping({"{reviewid}"})
    public ResponseDto<?> getComments(@PathVariable("reviewid") int reviewId,
                                      @ModelAttribute GetCommentsRequest getCommentsRequest
    ) {
        return new ResponseDto<>(HttpStatus.CREATED.value(), "댓글 목록 요청 성공",
                commentService.getComments(reviewId, getCommentsRequest));
    }

    @PutMapping({"{commentid}"})
    public ResponseDto<?> modifyReview(@PathVariable("commentid") int commentId,
                                       @RequestHeader("Authorization") String tokenHeader,
                                       @RequestBody ModifyCommentRequest modifyCommentRequest
    ) {
        String token = tokenHeader.substring(7);
        String memberId = jwtUtil.getMeberId(token);
        commentService.modifyComment(commentId, modifyCommentRequest, memberId);
        return new ResponseDto<>(HttpStatus.CREATED.value(), "리뷰 수정 완료", null);
    }
}

package com.dj.trip.domain.review.controller;

import com.dj.trip.domain.review.dto.request.CreateReviewRequest;
import com.dj.trip.domain.review.service.ReviewService;
import com.dj.trip.global.dto.ResponseDto;
import com.dj.trip.global.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final JWTUtil jwtUtil;

    @PostMapping
    public ResponseDto<?> createReview(@RequestHeader("Authorization") String tokenHeader,
                                       @RequestBody CreateReviewRequest reviewRequest
    ) {
        String token = tokenHeader.substring(7);
        String memberId = jwtUtil.getMeberId(token);
        return new ResponseDto<>(HttpStatus.CREATED.value(), "리뷰 작성 성공",
                reviewService.createReview(reviewRequest, memberId));
    }

    @GetMapping({"{reviewid}"})
    public ResponseDto<?> getReview(@RequestHeader("Authorization") String tokenHeader,
                                    @PathVariable("reviewid") int reviewId
    ) {
        String token = tokenHeader.substring(7);
        String memberId = jwtUtil.getMeberId(token);
        return new ResponseDto<>(HttpStatus.OK.value(), "리뷰 요청 성공",
                reviewService.getReview(reviewId, memberId));
    }
}

package com.dj.trip.domain.review.service;

import com.dj.trip.domain.review.Review;
import com.dj.trip.domain.review.dto.request.CreateReviewRequest;
import com.dj.trip.domain.review.dto.response.CreateReviewResponse;
import com.dj.trip.domain.review.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;

    // 리뷰 작성
    @Override
    @Transactional
    public CreateReviewResponse createReview(CreateReviewRequest reviewRequest, String memberId) {
        Review review = Review
                .createReview(
                        reviewRequest.attractionInfoId(),
                        memberId,
                        reviewRequest.title(),
                        reviewRequest.content(),
                        reviewRequest.imageUrl()
                );

        if (reviewMapper.insertReview(review) == 0) {
            throw new InsufficientAuthenticationException("잘못된 요청");
        }
        int id = reviewMapper.selectReviewId(review);
        return new CreateReviewResponse(id);
    }
}

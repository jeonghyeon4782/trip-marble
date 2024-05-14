package com.dj.trip.domain.review.service;

import com.dj.trip.domain.review.Review;
import com.dj.trip.domain.review.ReviewInfo;
import com.dj.trip.domain.review.ReviewsDao;
import com.dj.trip.domain.review.dto.request.CreateReviewRequest;
import com.dj.trip.domain.review.dto.request.GetReviewsRequest;
import com.dj.trip.domain.review.dto.request.ModifyReviewRequest;
import com.dj.trip.domain.review.dto.response.CreateReviewResponse;
import com.dj.trip.domain.review.dto.response.GetReviewResponse;
import com.dj.trip.domain.review.dto.response.GetReviewsResponse;
import com.dj.trip.domain.review.dto.response.ModifyReviewResponse;
import com.dj.trip.domain.review.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;

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

    @Override
    public GetReviewResponse getReview(int reviewId, String memberId) {
        Review review = Review
                .getReview(
                        reviewId,
                        memberId
                );
        return reviewMapper.selectReview(review);
    }

    @Override
    public GetReviewsResponse getReviews(GetReviewsRequest getReviewsRequest) {
        ReviewsDao reviewsDao = ReviewsDao
                .getReviews(
                        getReviewsRequest.key(),
                        getReviewsRequest.word(),
                        getReviewsRequest.order(),
                        getReviewsRequest.pagesize(),
                        getReviewsRequest.pageno() * getReviewsRequest.pagesize()
                );
        List<ReviewInfo> reviews = reviewMapper.selectReviews(reviewsDao);

        int page = getReviewsRequest.pageno() + 1;
        int total = reviewMapper.getTotalReviewsCount(reviewsDao);
        return new GetReviewsResponse(reviews, page, total);
    }

    @Override
    public ModifyReviewResponse modifyReview(int reviewId, ModifyReviewRequest modigyReviewRequest, String memberId) {
        Review review = Review
                .modifyReview(
                        reviewId,
                        modigyReviewRequest.attractionInfoId(),
                        memberId,
                        modigyReviewRequest.title(),
                        modigyReviewRequest.content(),
                        modigyReviewRequest.imageUrl()
                );
        if (reviewMapper.modifyReview(review) == 0) {
            throw new InsufficientAuthenticationException("잘못된 요청");
        }
        return new ModifyReviewResponse(review.getReviewId());
    }

    @Override
    public void deleteReview(int reviewId, String memberId) {
        Review review = Review
                .deleteReview(
                        reviewId,
                        memberId
                );
        if (reviewMapper.deleteReview(review) == 0) {
            throw new InsufficientAuthenticationException("잘못된 요청");
        }
    }
}

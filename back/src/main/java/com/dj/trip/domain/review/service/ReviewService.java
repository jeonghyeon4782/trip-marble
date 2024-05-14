package com.dj.trip.domain.review.service;

import com.dj.trip.domain.review.dto.request.CreateReviewRequest;
import com.dj.trip.domain.review.dto.request.GetReviewsRequest;
import com.dj.trip.domain.review.dto.response.CreateReviewResponse;
import com.dj.trip.domain.review.dto.response.GetReviewResponse;
import com.dj.trip.domain.review.dto.response.GetReviewsResponse;

public interface ReviewService {
    CreateReviewResponse createReview(CreateReviewRequest reviewRequest, String memberId);

    GetReviewResponse getReview(int reviewId, String memberId);

    GetReviewsResponse getReviews(GetReviewsRequest getReviewsRequest);
}

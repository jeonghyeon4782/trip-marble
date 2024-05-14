package com.dj.trip.domain.review.service;

import com.dj.trip.domain.review.dto.request.CreateReviewRequest;
import com.dj.trip.domain.review.dto.response.CreateReviewResponse;

public interface ReviewService {

    CreateReviewResponse createReview(CreateReviewRequest reviewRequest, String memberId);
}

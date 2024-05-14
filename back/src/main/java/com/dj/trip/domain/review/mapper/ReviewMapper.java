package com.dj.trip.domain.review.mapper;

import com.dj.trip.domain.review.Review;
import com.dj.trip.domain.review.ReviewInfo;
import com.dj.trip.domain.review.ReviewsDao;
import com.dj.trip.domain.review.dto.response.GetReviewResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    int insertReview(Review review);

    int selectReviewId(Review review);

    GetReviewResponse selectReview(Review review);

    List<ReviewInfo> selectReviews(ReviewsDao reviewsDao);

    int getTotalReviewsCount(ReviewsDao reviewsDao);

    int modifyReview(Review review);

    int deleteReview(Review review);

    int updateHit(int reviewId);
}

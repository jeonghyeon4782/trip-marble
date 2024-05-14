package com.dj.trip.domain.review.mapper;

import com.dj.trip.domain.review.Review;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {
    int insertReview(Review review);

    int selectReviewId(Review review);
}

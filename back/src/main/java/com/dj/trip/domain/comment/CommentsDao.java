package com.dj.trip.domain.comment;

import lombok.Builder;

@Builder
public record CommentsDao(
        int reviewId,
        int pageSize,   // 페이지 크기
        int start    // 시작 위치
) {
    public static CommentsDao getComments(int reviewId, int pageSize, int start) {
        return builder()
                .reviewId(reviewId)
                .pageSize(pageSize)
                .start(start)
                .build();
    }
}

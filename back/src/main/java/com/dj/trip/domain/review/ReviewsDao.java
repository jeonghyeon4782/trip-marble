package com.dj.trip.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public record ReviewsDao(
        String key, // {지역별, 키워드(제목, 내용)} 검색 기준
        String word,   // 검색 단어
        String order,   // 정렬 기준
        int pageSize,   // 페이지 크기
        int start    // 시작 위치
) {
    public static ReviewsDao getReviews(String key, String word, String order, int pageSize, int start) {
        return builder()
                .key(key)
                .word(word)
                .order(order)
                .pageSize(pageSize)
                .start(start)
                .build();
    }
}

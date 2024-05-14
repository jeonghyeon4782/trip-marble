package com.dj.trip.domain.review.dto.request;

public record GetReviewsRequest(
        String key, // {지역별, 키워드(제목, 내용)} 검색 기준
        String word,   // 검색 단어
        String order,   // 정렬 기준
        int pagesize,   // 페이지 크기
        int pageno    // 현재 페이지 번호
) {
}

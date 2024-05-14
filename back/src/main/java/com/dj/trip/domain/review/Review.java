package com.dj.trip.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    private int reviewId;
    private int attractionInfoId;
    private String memberId;
    private String title;
    private String content;
    private int hits;
    private String imageUrl;
    private Timestamp create_date;

    public static Review createReview(int attractionInfoId, String memberId, String title, String content, String imageUrl) {
        return builder()
                .attractionInfoId(attractionInfoId)
                .memberId(memberId)
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .build();
    }
}

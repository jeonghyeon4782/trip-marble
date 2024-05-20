package com.dj.trip.domain.member;

public record MemberInfo(
        String memberId,
        String nickname,
        String email,
        String imageUrl,
        Long reviews,
        Long score
) {
}

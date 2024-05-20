package com.dj.trip.domain.member.dto.response;

public record GetMemberResponse(
        String memberId,
        String nickname,
        String email,
        String image_url,
        Long reviews,
        Long score
) {
}

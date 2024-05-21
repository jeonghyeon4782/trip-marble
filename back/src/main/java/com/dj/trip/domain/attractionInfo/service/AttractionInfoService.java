package com.dj.trip.domain.attractionInfo.service;

import com.dj.trip.domain.attractionInfo.dto.GetAttarctionInfoResponseDto;
import com.dj.trip.domain.attractionInfo.dto.response.GetAttractionInfoByMemberIdResponse;

public interface AttractionInfoService {
    GetAttarctionInfoResponseDto getAttractionInfoNowLocation(int memberBoardMapId);

    GetAttractionInfoByMemberIdResponse getAttractionInfoByMemberId(String memberId);
}

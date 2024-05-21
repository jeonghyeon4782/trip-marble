package com.dj.trip.domain.attractionInfo.service;

import com.dj.trip.domain.attractionInfo.AttractionInfoVo;
import com.dj.trip.domain.attractionInfo.dto.GetAttarctionInfoResponseDto;
import com.dj.trip.domain.attractionInfo.dto.response.GetAttractionInfoByMemberIdResponse;

import java.util.List;

public interface AttractionInfoService {
    GetAttarctionInfoResponseDto getAttractionInfoNowLocation(int memberBoardMapId);

    GetAttractionInfoByMemberIdResponse getAttractionInfoByMemberId(String memberId);

    List<AttractionInfoVo> getTop4AttractionInfo();
}

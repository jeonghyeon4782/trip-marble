package com.dj.trip.domain.attractionInfo.service;

import com.dj.trip.domain.attractionInfo.dto.GetAttarctionInfoResponseDto;

public interface AttractionInfoService {
    GetAttarctionInfoResponseDto getAttractionInfoNowLocation(int memberBoardMapId);
}

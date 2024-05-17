package com.dj.trip.domain.attractionInfo.Controller;

import com.dj.trip.domain.attractionInfo.dto.GetAttarctionInfoResponseDto;
import com.dj.trip.domain.attractionInfo.service.AttractionInfoService;
import com.dj.trip.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attraction-info")
public class AttractionInfoController {

    private final AttractionInfoService attractionInfoService;

    // memberBoardMapId로 현재 캐릭터 위치에 있는 관광지 정보 조회
    @GetMapping("/{memberBoardMapId}")
    public ResponseDto<GetAttarctionInfoResponseDto> getAttractionInfoNowLocation(@PathVariable("memberBoardMapId") int memberBoardMapId) {
        return new ResponseDto<>(HttpStatus.OK.value(), "관광지 조회 성공", attractionInfoService.getAttractionInfoNowLocation(memberBoardMapId));
    }
}

package com.dj.trip.domain.attractionInfo.mapper;

import com.dj.trip.domain.attractionInfo.AttractionInfoVo;
import com.dj.trip.domain.attractionInfo.dto.response.GetAttractionInfoByMemberIdResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttractionInfoMapper {

    // sidoId로 관광지 정보 리스트 조회
    List<AttractionInfoVo> selectAttractionInfoListBySidoId(int sidoId);

    // memberBoardMapId로 현재 캐릭터 위치에 있는 관광지 정보 조회
    AttractionInfoVo selectAttractionInfoByMemberBoardMapId(int memberBoardMapId);

    GetAttractionInfoByMemberIdResponse selectAttractionInfoByMemberId(String memberId);

    List<AttractionInfoVo> selectTop4AttractionInfo();
}

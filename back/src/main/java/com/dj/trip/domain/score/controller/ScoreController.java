package com.dj.trip.domain.score.controller;

import com.dj.trip.domain.score.dto.GetScoreResponseDto;
import com.dj.trip.domain.score.dto.UpdateScoreRequestDto;
import com.dj.trip.domain.score.service.ScoreService;
import com.dj.trip.global.dto.ResponseDto;
import com.dj.trip.global.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/score")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ScoreController {

    private final ScoreService scoreService;
    private final JWTUtil jwtUtil;

    @GetMapping("/{sidoId}")
    public ResponseDto<List<GetScoreResponseDto>> getScore(@PathVariable("sidoId") int sidoId, @RequestHeader("Authorization") String tokenHeader) {
        String memberId = jwtUtil.getMeberId(tokenHeader.substring(7));
        return new ResponseDto<>(HttpStatus.OK.value(), "점수 조회 성공", scoreService.getScore(sidoId, memberId));
    }

    @PutMapping("/{sidoId}")
    public ResponseDto<String> updateScore(@PathVariable("sidoId") int sidoId, @RequestHeader("Authorization") String tokenHeader, @RequestBody UpdateScoreRequestDto updateScoreRequestDto) {
        String memberId = jwtUtil.getMeberId(tokenHeader.substring(7));
        scoreService.updateScore(sidoId, memberId, updateScoreRequestDto.getPlusScore());
        return new ResponseDto<>(HttpStatus.OK.value(), "점수 올리기 성공", null);
    }
}

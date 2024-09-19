package com.uplus.miniproject2.controller;

import com.uplus.miniproject2.entity.hobby.Hobby;
import com.uplus.miniproject2.entity.proflie.MBTI;
import com.uplus.miniproject2.service.StatisticsService;
import com.uplus.miniproject2.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hobbies")
public class StatisticsController {
    private StatisticsService statisticsService;

    @GetMapping("/mbti")
    public ApiUtil.ApiSuccess<Map<MBTI, Long>> getMbtiCounts() {
        Map<MBTI, Long> mbtiCounts = statisticsService.getMbtiCounts();
        return ApiUtil.success(mbtiCounts);
    }
}
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
@RequestMapping("/api")
public class StatisticsController {
    private final StatisticsService statisticsService;


    // MBTI 작업
    @GetMapping("/mbti")
    public ApiUtil.ApiSuccess<Map<MBTI, Long>> getMbtiCounts() {
        Map<MBTI, Long> mbtiCounts = statisticsService.getMbtiCounts();
        return ApiUtil.success(mbtiCounts);
    }


    // 성별 비율 작업
    @GetMapping("/gender")
    public ApiUtil.ApiSuccess<Map<String, Integer>> getGenderRatio() {
        Map<String, Integer> genderRatio = statisticsService.getGenderRatio();
        return ApiUtil.success(genderRatio);
    }

    // 전공별 수 작업
    @GetMapping("/major")
    public ApiUtil.ApiSuccess<Map<String, Long>> getMajorCounts() {
        Map<String, Long> majorCounts = statisticsService.getMajorCounts();
        return ApiUtil.success(majorCounts);
    }

    // 취미 작업
    @GetMapping("/hobbies")
    public ApiUtil.ApiSuccess<Map<String, Long>> getHobbyCounts() {
        Map<String, Long> hobbyCounts = statisticsService.getHobbyCounts();
        return ApiUtil.success(hobbyCounts);
    }

    // 사는 지역 작업
    @GetMapping("/region")
    public ApiUtil.ApiSuccess<Map<String, Long>> getRegionCounts() {
        Map<String, Long> regionCounts = statisticsService.getRegionCounts();
        return ApiUtil.success(regionCounts);
    }
}

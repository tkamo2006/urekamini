package com.uplus.miniproject2.controller;

import com.uplus.miniproject2.dto.StatisticsDto;
import com.uplus.miniproject2.service.StatisticsService;
import com.uplus.miniproject2.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    // MBTI 작업
    @GetMapping("/mbti")
    public ApiUtil.ApiSuccess<List<StatisticsDto>> getMbtiCounts() {
        List<StatisticsDto> mbtiCounts = statisticsService.getMbtiCounts();
        return ApiUtil.success(mbtiCounts);
    }

    // 성별 비율 작업
    @GetMapping("/gender")
    public ApiUtil.ApiSuccess<List<StatisticsDto>> getGenderRatio() {
        List<StatisticsDto> genderRatio = statisticsService.getGenderRatio();
        return ApiUtil.success(genderRatio);
    }

    // 전공별 수 작업
    @GetMapping("/major")
    public ApiUtil.ApiSuccess<List<StatisticsDto>> getMajorCounts() {
        List<StatisticsDto> majorCounts = statisticsService.getMajorCounts();
        return ApiUtil.success(majorCounts);
    }

    // 취미 작업
    @GetMapping("/hobbies")
    public ApiUtil.ApiSuccess<List<StatisticsDto>> getHobbyCounts() {
        List<StatisticsDto> hobbyCounts = statisticsService.getHobbyCounts();
        return ApiUtil.success(hobbyCounts);
    }

    // 사는 지역 작업
    @GetMapping("/region")
    public ApiUtil.ApiSuccess<List<StatisticsDto>> getRegionCounts() {
        List<StatisticsDto> regionCounts = statisticsService.getRegionCounts();
        return ApiUtil.success(regionCounts);
    }
}

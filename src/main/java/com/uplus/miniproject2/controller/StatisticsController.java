package com.uplus.miniproject2.controller;

import com.uplus.miniproject2.entity.hobby.Hobby;
import com.uplus.miniproject2.service.StatisticsService;
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

    @GetMapping("/counts")
    public Map<List<Hobby>, Long> getHobbiesCount() {
        return statisticsService.getHobbiesCount();
    }
}
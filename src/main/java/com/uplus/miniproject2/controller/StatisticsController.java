package com.uplus.miniproject2.controller;

import com.uplus.miniproject2.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/hobbies")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/counts")
    public Map<String, Long> getHobbiesCount() {
        return statisticsService.getHobbiesCount();
    }
}

package com.uplus.miniproject2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsDto {
    private String type; // "mbti", "gender", "major", "region", "hobby" 등을 구분
    private String name; // 이름
    private Long count;  // 수
    private Integer percentage; // 비율

}

package com.uplus.miniproject2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MainPageUserDto {

    private String name;
    private Long userId;
    private String major;
    private String mbti;
    private String region;
    private byte[] profileImage;
}

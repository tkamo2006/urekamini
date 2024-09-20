package com.uplus.miniproject2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
    private String mbti;
    private String region;
    private String major;
    private String niceExperience;
    private String plan;
    private byte[] profileImage;
}

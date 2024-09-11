package com.uplus.miniproject2.dto;

import com.uplus.miniproject2.entity.hobby.Hobby;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetailPageUserDto {
    private String name;
    private String gender;
    private String mbti;
    private String region;
    private String major;
    private List<Hobby> hobbies;
    private String plan;
    private byte[] profileImage;
}

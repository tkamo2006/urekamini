package com.uplus.miniproject2.dto;

import com.uplus.miniproject2.entity.hobby.Hobby;
import com.uplus.miniproject2.entity.hobby.HobbyCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HobbyBoardDto {
    private Long id;
    private String name;
    private Long userId;
    private String title;
    private String description;
    private String videoLink;
    private HobbyCategory hobbyCategory;
    private int thumbsUp;
}

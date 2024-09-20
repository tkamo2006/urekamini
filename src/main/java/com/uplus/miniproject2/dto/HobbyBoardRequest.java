package com.uplus.miniproject2.dto;

import com.uplus.miniproject2.entity.hobby.HobbyCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HobbyBoardRequest {
    private String title;
    private String description;
    private HobbyCategory hobbyCategory;
    private String videoLink;
}
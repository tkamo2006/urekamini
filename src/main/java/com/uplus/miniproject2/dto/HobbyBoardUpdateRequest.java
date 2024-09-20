package com.uplus.miniproject2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HobbyBoardUpdateRequest {
    private String title;
    private String description;
    private String videoLink;
    private String hobbyCategory;
}

package com.uplus.miniproject2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FamiliarityResultDto {

    private double nameScore;
    private double mbtiScore;
    private double hobbyScore;
    private double finalScore;
}

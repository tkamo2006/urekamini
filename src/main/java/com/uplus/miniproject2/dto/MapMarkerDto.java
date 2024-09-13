package com.uplus.miniproject2.dto;

import com.uplus.miniproject2.entity.proflie.Profile;
import com.uplus.miniproject2.entity.proflie.Region;
import com.uplus.miniproject2.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MapMarkerDto {
    private Long userId;
    private String userName;
    private String regionName;
    private Double latitude;
    private Double longitude;
}

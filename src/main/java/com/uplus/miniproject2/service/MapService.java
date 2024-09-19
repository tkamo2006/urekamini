package com.uplus.miniproject2.service;

import com.uplus.miniproject2.dto.MapMarkerDto;
import com.uplus.miniproject2.entity.proflie.Profile;

import java.util.List;

public interface MapService {
    List<MapMarkerDto> findAllProfiles();
}

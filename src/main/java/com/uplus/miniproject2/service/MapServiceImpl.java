package com.uplus.miniproject2.service;

import com.uplus.miniproject2.dto.MapMarkerDto;
import com.uplus.miniproject2.entity.proflie.Profile;
import com.uplus.miniproject2.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {
    private final MapRepository mapRepository;

    @Override
    public List<MapMarkerDto> findAllProfiles() {
        try {
            List<Profile> profiles = mapRepository.findAll();

            return profiles.stream()
                    .map(profile -> new MapMarkerDto(profile.getUser().getId(),
                            profile.getUser().getName(),
                            profile.getRegion().getName(),
                            profile.getRegion().getLatitude(),
                            profile.getRegion().getLongitude()))
                    .toList();
        } catch (Exception e) {
            // 로그를 남기거나, 적절한 예외를 처리
            throw new RuntimeException("프로필 목록을 가져오는 중 오류 발생", e);
        }
    }
}

package com.uplus.miniproject2.service;

import com.uplus.miniproject2.dto.StatisticsDto;
//import com.uplus.miniproject2.entity.profile.MBTI;
//import com.uplus.miniproject2.entity.profile.Profile;
import com.uplus.miniproject2.entity.proflie.Profile;
import com.uplus.miniproject2.repository.StatisticsRepository;
import com.uplus.miniproject2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final UserRepository userRepository;

    // MBTI 작업
    public List<StatisticsDto> getMbtiCounts() {
        List<Profile> profiles = statisticsRepository.findAll();
        return profiles.stream()
                .collect(Collectors.groupingBy(Profile::getMbti, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new StatisticsDto("mbti", entry.getKey().name(), entry.getValue(), null))
                .collect(Collectors.toList());
    }

    // 남녀 비율 작업
    public List<StatisticsDto> getGenderRatio() {
        long maleCount = userRepository.countByGender("male");
        long femaleCount = userRepository.countByGender("female");
        long total = maleCount + femaleCount;

        List<StatisticsDto> genderRatio = List.of(
                new StatisticsDto("gender", "male", maleCount, total > 0 ? (int) ((maleCount * 100) / total) : 0),
                new StatisticsDto("gender", "female", femaleCount, total > 0 ? (int) ((femaleCount * 100) / total) : 0)
        );

        return genderRatio;
    }

    // 전공 목록 출력
    public List<StatisticsDto> getMajorCounts() {
        List<Object[]> results = statisticsRepository.findMajorCounts();
        return results.stream()
                .map(record -> new StatisticsDto("major", (String) record[0], (Long) record[1], null))
                .collect(Collectors.toList());
    }

    // 취미 이름, 수
    public List<StatisticsDto> getHobbyCounts() {
        List<Object[]> results = statisticsRepository.findHobbyCounts();
        return results.stream()
                .map(record -> new StatisticsDto("hobby", (String) record[0], (Long) record[1], null))
                .collect(Collectors.toList());
    }

    // 거주 지역
    public List<StatisticsDto> getRegionCounts() {
        List<Object[]> results = statisticsRepository.findRegionCounts();
        return results.stream()
                .map(record -> new StatisticsDto("region", (String) record[0], (Long) record[1], null))
                .collect(Collectors.toList());
    }
}

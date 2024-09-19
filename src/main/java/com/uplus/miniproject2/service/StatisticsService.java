package com.uplus.miniproject2.service;

import com.uplus.miniproject2.entity.hobby.Hobby;
import com.uplus.miniproject2.entity.proflie.MBTI;
import com.uplus.miniproject2.entity.proflie.Profile;
import com.uplus.miniproject2.repository.StatisticsRepository;
import com.uplus.miniproject2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Map<MBTI, Long> getMbtiCounts() {
        List<Profile> profiles = statisticsRepository.findAll();
        return profiles.stream()
                .collect(Collectors.groupingBy(Profile::getMbti, Collectors.counting()));
    }
    // 남여 비율 작업
    public Map<String, Integer> getGenderRatio() {
        long maleCount = userRepository.countByGender("male");
        long femaleCount = userRepository.countByGender("female");

        long total = maleCount + femaleCount; // 총 수

        Map<String, Integer> genderRatio = new HashMap<>();
        genderRatio.put("male", total > 0 ? (int) ((maleCount * 100) / total) : 0);
        genderRatio.put("female", total > 0 ? (int) ((femaleCount * 100) / total) : 0);

        return genderRatio;
    }

    // 전공 목록 출력
    public Map<String, Long> getMajorCounts() {
        List<Object[]> results = statisticsRepository.findMajorCounts();
        return results.stream()
                .collect(Collectors.toMap(
                        record -> (String) record[0],  // 전공 이름
                        record -> (Long) record[1]      // 전공 수
                ));
    }

    // 전공 목록 출력 (중복 제거)
    public List<String> getAllMajors() {
        return statisticsRepository.findDistinctMajors();
    }

    // 취미 이름, 수
    public Map<String, Long> getHobbyCounts() {
        List<Object[]> results = statisticsRepository.findHobbyCounts();
        return results.stream()
                .collect(Collectors.toMap(
                        record -> (String) record[0],  // 취미 이름
                        record -> (Long) record[1]      // 취미 수
                ));
    }


    // 거주 지역
    public Map<String, Long> getRegionCounts() {
        List<Object[]> results = statisticsRepository.findRegionCounts();
        return results.stream()
                .collect(Collectors.toMap(
                        record -> (String) record[0],  // 지역 이름
                        record -> (Long) record[1]      // 지역 수
                ));
    }

}
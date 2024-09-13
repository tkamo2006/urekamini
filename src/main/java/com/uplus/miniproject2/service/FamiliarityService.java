package com.uplus.miniproject2.service;

import com.uplus.miniproject2.dto.FamiliarityResultDto;
import com.uplus.miniproject2.entity.hobby.Hobby;
import com.uplus.miniproject2.entity.proflie.MBTI;
import com.uplus.miniproject2.entity.user.User;
import com.uplus.miniproject2.repository.UserRepository;
import com.uplus.miniproject2.util.HobbyFamiliarityCalculator;
import com.uplus.miniproject2.util.MbtiFamiliarity;
import com.uplus.miniproject2.util.NameFamiliarityCalculator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FamiliarityService {

    private final UserRepository userRepository;

    public FamiliarityResultDto calculateFamiliarity(Long loginUserId, Long clickedUserId) {
        User loginUser = userRepository.findByIdWithProfileAndHobbies(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException());

        User clickedUser = userRepository.findByIdWithProfileAndHobbies(clickedUserId)
                .orElseThrow(() -> new IllegalArgumentException());

        if (loginUser.getProfile() == null) {
            throw new IllegalStateException("프로필 등록이 필요합니다.");
        }

        double nameFamiliarity = calculateNameFamiliarity(loginUser.getName(), clickedUser.getName());
        double mbtiFamiliarity = getMbtiFamiliarity(loginUser.getProfile().getMbti(), clickedUser.getProfile().getMbti());
        double hobbyFamiliarity = calculateHobbyFamiliarity(loginUser.getProfile().getHobbies(), clickedUser.getProfile().getHobbies());
        double finalScore = nameFamiliarity * 0.15 + mbtiFamiliarity * 0.6 + hobbyFamiliarity * 0.25;

        return new FamiliarityResultDto(nameFamiliarity, mbtiFamiliarity, hobbyFamiliarity, finalScore);
    }

    private double calculateNameFamiliarity(String loginUserName, String clickUserName) {
        return NameFamiliarityCalculator.calculateNameFamiliarity(loginUserName, clickUserName);
    }

    private double getMbtiFamiliarity(MBTI loginUserMbti, MBTI clickUserMbti) {
        return MbtiFamiliarity.getFamiliarityScore(loginUserMbti, clickUserMbti);
    }

    private double calculateHobbyFamiliarity(List<Hobby> loginUserHobbies, List<Hobby> clickUserHobbies) {
        return HobbyFamiliarityCalculator.calculateHobbyFamiliarity(loginUserHobbies, clickUserHobbies);
    }
}

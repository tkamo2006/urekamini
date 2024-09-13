package com.uplus.miniproject2.service;

import com.uplus.miniproject2.entity.proflie.Profile;
import com.uplus.miniproject2.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileImageService {

    private final ProfileRepository profileRepository;


    public void updateProfileImage(Long userId, String imagePath) {
        try {
            File file = new File(imagePath);
            byte[] imageData = Files.readAllBytes(file.toPath());

            // 해당 userId로 프로필 조회
            Profile profile = profileRepository.findByUserId(userId);

            if (profile != null) {
                profile.updateImage(imageData);
                profileRepository.save(profile);  // DB에 저장
            } else {
                System.out.println("해당 유저의 프로필을 찾을 수 없습니다.");
            }
        } catch (IOException e) {
            System.out.println("파일을 읽어오는 중 오류 발생: " + e.getMessage());
        }
    }
}

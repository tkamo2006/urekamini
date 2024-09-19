package com.uplus.miniproject2.controller;

import com.uplus.miniproject2.dto.ProfilePageProfileRequestDto;
import com.uplus.miniproject2.dto.ProfilePageProfileResponseDto;
import com.uplus.miniproject2.entity.hobby.Hobby;
import com.uplus.miniproject2.entity.proflie.Profile;
import com.uplus.miniproject2.entity.proflie.ProfileRequest;
import com.uplus.miniproject2.entity.proflie.RequestType;
import com.uplus.miniproject2.service.ProfileService;
import com.uplus.miniproject2.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public ApiUtil.ApiSuccess<?> createProfileRequest(
            @RequestParam("userId") Long userId,
            @RequestParam("mbti") String mbti,
            @RequestParam("major") String major,
            @RequestParam("region") String region,
            @RequestParam("plan") String plan,
            @RequestParam("niceExperience") String niceExperience,
            @RequestParam("profileImage") MultipartFile profileImage,
            @RequestParam("hobbies") List<Hobby> hobbies
    ) throws IOException {

        byte[] imageBytes = profileImage.getBytes();

        ProfilePageProfileResponseDto profileResponseDto
                = new ProfilePageProfileResponseDto(major, mbti, region, plan, niceExperience,imageBytes, hobbies);
        ProfilePageProfileRequestDto profileRequest = profileService.createProfileRequest(userId, profileResponseDto);
        return ApiUtil.success(profileRequest);
    }

    @GetMapping
    public ApiUtil.ApiSuccess<?> getProfileRequests(@RequestParam("adminId") Long adminId) {
        List<ProfilePageProfileRequestDto> requests = profileService.getProfileRequests(adminId);

        return ApiUtil.success(requests);
    }




//    @PostMapping("/profile/{userId}/image")
//    public ResponseEntity<?> uploadProfileImage(
//            @PathVariable Long userId,
//            @RequestParam("profileImage") MultipartFile profileImage) throws IOException {
//
//        // 이미지 파일을 byte[]로 변환
//        byte[] imageBytes = profileImage.getBytes();
//
//        // User를 통해 Profile 엔티티 찾기
//        Profile profile = profileService.findByUserId(userId);
//        if (profile == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // 이미지 업데이트
//        profile.updateImage(imageBytes);
//        profileService.save(profile); // 저장
//
//        return ResponseEntity.ok().body("Image uploaded successfully.");
//    }
//
//
//    @GetMapping("/profile/{userId}/image")
//    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long userId) {
//
//        // User를 통해 Profile 엔티티 찾기
//        Profile profile = profileService.findByUserId(userId);
//        if (profile == null || profile.getImage() == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // 이미지 반환
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG); // 이미지 타입에 따라 변경
//        return new ResponseEntity<>(profile.getImage(), headers, HttpStatus.OK);
//    }
}

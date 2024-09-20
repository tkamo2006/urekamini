package com.uplus.miniproject2.controller;

import com.uplus.miniproject2.dto.ProfileExistDto;
import com.uplus.miniproject2.dto.ProfilePageProfileRequestDto;
import com.uplus.miniproject2.dto.ProfilePageProfileResponseDto;
import com.uplus.miniproject2.entity.user.CustomUserDetails;
import com.uplus.miniproject2.service.ProfileService;
import com.uplus.miniproject2.util.ApiUtil;
import com.uplus.miniproject2.util.ApiUtil.ApiSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.uplus.miniproject2.dto.ProfileRequestDto;
import com.uplus.miniproject2.entity.hobby.Hobby;
import com.uplus.miniproject2.entity.proflie.Profile;
import com.uplus.miniproject2.entity.proflie.ProfileRequest;
import com.uplus.miniproject2.entity.proflie.RequestType;
import com.uplus.miniproject2.service.ProfileService;
import com.uplus.miniproject2.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public ApiUtil.ApiSuccess<?> createProfileRequest(
            @AuthenticationPrincipal CustomUserDetails loginUser,
            @RequestParam("mbti") String mbti,
            @RequestParam("major") String major,
            @RequestParam("region") String region,
            @RequestParam("plan") String plan,
            @RequestParam("niceExperience") String niceExperience,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam("subHobbies") String subHobbiesJson
    ) throws IOException {


        Long userId = loginUser.getId();

        System.out.println("userId : "+userId);

        List<String> subHobbies = profileService.parseJsonArray(subHobbiesJson);
        byte[] imageBytes = profileImage != null ? profileImage.getBytes() : Objects.requireNonNull(getClass().getResourceAsStream("/static/img/img.png")).readAllBytes();

        ProfilePageProfileResponseDto profileResponseDto
                = new ProfilePageProfileResponseDto(major, mbti, region, plan, niceExperience,imageBytes, subHobbies);
        ProfilePageProfileRequestDto profileRequest = profileService.createProfileRequest(userId, profileResponseDto);
        return ApiUtil.success(profileRequest);
    }


//    @GetMapping
//    public ApiUtil.ApiSuccess<?> getProfileRequests(@RequestParam("adminId") Long adminId) {
//        List<ProfilePageProfileRequestDto> requests = profileService.getProfileRequests(adminId);
//
//        return ApiUtil.success(requests);
//    }

    @GetMapping
    public  ApiUtil.ApiSuccess<?> getProfileRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ProfileRequestDto> profileRequests = profileService.getProfileRequests(page, size);
        return ApiUtil.success(profileRequests);
    }

    @GetMapping("/check")
    public ApiSuccess<?> checkHasProfile(@AuthenticationPrincipal CustomUserDetails loginUser) {
        Long loginUserId = loginUser.getId();
        ProfileExistDto profileExistDto = profileService.getProfile(loginUserId);

        return ApiUtil.success(profileExistDto);
    }

}

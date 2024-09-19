package com.uplus.miniproject2.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public ApiUtil.ApiSuccess<?> createProfileRequest(
//            @RequestParam("userId") Long userId,
            @RequestParam("mbti") String mbti,
            @RequestParam("major") String major,
            @RequestParam("region") String region,
            @RequestParam("plan") String plan,
            @RequestParam("niceExperience") String niceExperience,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam("subHobbies") String subHobbiesJson
    ) throws IOException {



        List<String> subHobbies = profileService.parseJsonArray(subHobbiesJson);
        byte[] imageBytes = profileImage != null ? profileImage.getBytes() : Objects.requireNonNull(getClass().getResourceAsStream("/static/img.png")).readAllBytes();

        ProfilePageProfileResponseDto profileResponseDto
                = new ProfilePageProfileResponseDto(major, mbti, region, plan, niceExperience,imageBytes, subHobbies);
//        ProfilePageProfileRequestDto profileRequest = profileService.createProfileRequest(userId, profileResponseDto);
        ProfilePageProfileRequestDto profileRequest = profileService.createProfileRequest(10L, profileResponseDto);
        return ApiUtil.success(profileRequest);
    }

    @GetMapping
    public ApiUtil.ApiSuccess<?> getProfileRequests(@RequestParam("adminId") Long adminId) {
        List<ProfilePageProfileRequestDto> requests = profileService.getProfileRequests(adminId);

        return ApiUtil.success(requests);
    }
}

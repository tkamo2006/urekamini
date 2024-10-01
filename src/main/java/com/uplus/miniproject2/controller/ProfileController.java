package com.uplus.miniproject2.controller;

import com.uplus.miniproject2.dto.ProfileExistDto;
import com.uplus.miniproject2.dto.ProfilePageProfileRequestDto;
import com.uplus.miniproject2.dto.ProfilePageProfileResponseDto;
import com.uplus.miniproject2.dto.ProfileResponse;
import com.uplus.miniproject2.entity.proflie.Profile;
import com.uplus.miniproject2.entity.user.CustomUserDetails;
import com.uplus.miniproject2.service.ProfileService;
import com.uplus.miniproject2.util.ApiUtil;
import com.uplus.miniproject2.util.ApiUtil.ApiSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.uplus.miniproject2.dto.ProfileRequestDto;
import org.springframework.data.domain.Page;
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
            @RequestParam("subHobbies") List<String> subHobbies
    ) throws IOException {


        Long userId = loginUser.getId();
        byte[] imageBytes = profileImage != null ?
                profileImage.getBytes() : Objects.requireNonNull(getClass().getResourceAsStream("/static/img/img.png")).readAllBytes();

        ProfilePageProfileResponseDto profileResponseDto
                = new ProfilePageProfileResponseDto(major, mbti, region, plan, niceExperience,imageBytes, subHobbies);
        ProfilePageProfileRequestDto profileRequest = profileService.createProfileRequest(userId, profileResponseDto);
        return ApiUtil.success(profileRequest);
    }



    @GetMapping("/upload")
    public ApiUtil.ApiSuccess<?> getUserProfile(@AuthenticationPrincipal CustomUserDetails loginUser){
        Profile profile = profileService.getProfileByUserId(loginUser.getId());
        ProfileResponse profileResponse = new ProfileResponse();

        if (profile != null) {
            profileResponse = new ProfileResponse(
                    profile.getMbti().name(),
                    profile.getRegion().getName(),
                    profile.getMajor(),
                    profile.getNiceExperience(),
                    profile.getPlan(),
                    profile.getImage()
            );
        }
        return ApiUtil.success(profileResponse);
    }


    @GetMapping
    public  ApiUtil.ApiSuccess<?> getProfileRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ProfileRequestDto> profileRequests = profileService.getProfileRequests(page, size);
        return ApiUtil.success(profileRequests);
    }

    @PutMapping("/{requestId}")
    public ApiUtil.ApiSuccess<?> updateProfileRequest(@PathVariable Long requestId, @RequestBody ProfileRequestDto requestDto) {
        profileService.updateProfileRequest(requestId, requestDto.getRequestStatusCodeKey());
        return ApiUtil.success("Profile request status updated successfully");
    }

    @GetMapping("/check")
    public ApiSuccess<?> checkHasProfile(@AuthenticationPrincipal CustomUserDetails loginUser) {
        Long loginUserId = loginUser.getId();
        ProfileExistDto profileExistDto = profileService.getProfile(loginUserId);

        return ApiUtil.success(profileExistDto);
    }

}

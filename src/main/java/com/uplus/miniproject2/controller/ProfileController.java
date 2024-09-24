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
        byte[] imageBytes = profileImage != null ? profileImage.getBytes() : Objects.requireNonNull(getClass().getResourceAsStream("/static/img/img.png")).readAllBytes();

        ProfilePageProfileResponseDto profileResponseDto
                = new ProfilePageProfileResponseDto(major, mbti, region, plan, niceExperience,imageBytes, subHobbies);
        ProfilePageProfileRequestDto profileRequest = profileService.createProfileRequest(userId, profileResponseDto);
        return ApiUtil.success(profileRequest);
    }



    @GetMapping("/upload")
    public ApiUtil.ApiSuccess<?> getUserProfile(@AuthenticationPrincipal CustomUserDetails loginUser) throws IOException {
        Profile profile = profileService.getProfileByUserId(loginUser.getId());
        ProfileResponse profileResponse = new ProfileResponse();
//        String base64Image = "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";
//        byte[] imageData = Base64.getDecoder().decode(base64Image);
//        profileResponse.setProfileImage(imageData);

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
        profileService.updateProfileRequest(requestId, requestDto.getRequestStatus());
        return ApiUtil.success("Profile request status updated successfully");
    }

    @GetMapping("/check")
    public ApiSuccess<?> checkHasProfile(@AuthenticationPrincipal CustomUserDetails loginUser) {
        Long loginUserId = loginUser.getId();
        ProfileExistDto profileExistDto = profileService.getProfile(loginUserId);

        return ApiUtil.success(profileExistDto);
    }

}

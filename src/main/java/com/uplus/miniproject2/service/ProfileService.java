package com.uplus.miniproject2.service;

import com.uplus.miniproject2.dto.ProfilePageProfileRequestDto;
import com.uplus.miniproject2.dto.ProfilePageProfileResponseDto;
import com.uplus.miniproject2.entity.proflie.*;
import com.uplus.miniproject2.entity.user.Role;
import com.uplus.miniproject2.entity.user.User;
import com.uplus.miniproject2.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRequestRepository profileRequestRepository;
    private final CustomUserRepository userRepository;

    public ProfilePageProfileRequestDto createProfileRequest(Long userId,
                                                             ProfilePageProfileResponseDto profileResponseDto) {
        User user = userRepository.findById(userId);
        RequestType requestType;
        Profile profile;
        Region region;
        // 사용자의 기존 프로필이 있는지 확인
        if (user.getProfile() == null) {
            requestType = RequestType.REGISTER;

            // 프로필이 없는 경우 새로운 프로필 생성
            region = new Region(profileResponseDto.getRegion(), 0, 0); // 지역 입력 방식은 별도로 처리 필요
            profile = Profile.builder()
                    .user(user)
                    .mbti(MBTI.valueOf(profileResponseDto.getMbti()))
                    .region(region)
                    .major(profileResponseDto.getMajor())
                    .plan(profileResponseDto.getPlan())
                    .niceExperience(profileResponseDto.getNiceExperience())
                    .hobbies(profileResponseDto.getHobbies())
                    .image(profileResponseDto.getProfileImage())
                    .build();

        } else {
            requestType = RequestType.UPDATE;
            region = new Region(profileResponseDto.getRegion(), 0, 0); // 지역 입력 방식은 별도로 처리 필요
            // 기존 프로필이 있는 경우 업데이트
            profile = user.getProfile();
            profile.updateProfile(MBTI.valueOf(profileResponseDto.getMbti()), region, profileResponseDto.getMajor(),
                    profileResponseDto.getPlan(), profileResponseDto.getNiceExperience(),
                    profileResponseDto.getHobbies(), profileResponseDto.getProfileImage());
        }

        ProfileRequest request = ProfileRequest.builder()
                .user(user)
                .profile(profile)
                .requestType(requestType)
                .requestStatus(RequestStatus.PENDING)
                .build();
        profileRequestRepository.save(request);

        return new ProfilePageProfileRequestDto(
                profile.getMajor(),
                profile.getMbti().name(),
                profile.getRegion().getName(),
                profile.getPlan(),
                profile.getNiceExperience(),
                profile.getImage(),
                profile.getHobbies(),
                request.getRequestType(),
                request.getRequestStatus()
        );
    }

    public List<ProfilePageProfileRequestDto> getProfileRequests(Long adminId) {
        User admin = userRepository.findById(adminId);
        if (admin.getRole() != Role.ADMIN) {
            System.out.println();
            throw new IllegalStateException("접근 권한 없음");
        }
        List<ProfileRequest> profileRequests = profileRequestRepository.findAllByRequestStatus(RequestStatus.PENDING);

        for (ProfileRequest request : profileRequests) {
            System.out.println(request);
        }

        // DTO로 변환
        List<ProfilePageProfileRequestDto> profileRequestDTOs = profileRequests.stream()
                .map(request -> new ProfilePageProfileRequestDto(
                        request.getProfile().getMajor(),
                        request.getProfile().getMbti().name(),
                        request.getProfile().getRegion().getName(),
                        request.getProfile().getPlan(),
                        request.getProfile().getNiceExperience(),
                        request.getProfile().getImage(),
                        request.getProfile().getHobbies(),
                        request.getRequestType(),
                        request.getRequestStatus()
                ))
                .collect(Collectors.toList());

        return profileRequestDTOs;

    }
}

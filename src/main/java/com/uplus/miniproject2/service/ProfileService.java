package com.uplus.miniproject2.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uplus.miniproject2.dto.ProfilePageProfileRequestDto;
import com.uplus.miniproject2.dto.ProfilePageProfileResponseDto;
import com.uplus.miniproject2.entity.hobby.Hobby;
import com.uplus.miniproject2.entity.proflie.*;
import com.uplus.miniproject2.entity.user.Role;
import com.uplus.miniproject2.entity.user.User;
import com.uplus.miniproject2.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRequestRepository profileRequestRepository;
    private final UserRepository userRepository;
    private final HobbyRepository hobbyRepository;

    public ProfilePageProfileRequestDto createProfileRequest(Long userId,
                                                             ProfilePageProfileResponseDto profileResponseDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException());
        System.out.println(userId);
        RequestType requestType;
        Profile profile;
        Region region;

        // 취미 이름 목록을 데이터베이스에서 한 번에 조회
        List<Hobby> allHobbies = hobbyRepository.findAllByNameIn(profileResponseDto.getHobbies());
        Map<String, Hobby> hobbyMap = allHobbies.stream()
                .collect(Collectors.toMap(
                        Hobby::getName,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        List<Hobby> hobbies = profileResponseDto.getHobbies().stream()
                .map(hobbyMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

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
                    .hobbies(hobbies)
                    .image(profileResponseDto.getProfileImage())
                    .build();

        } else {
            requestType = RequestType.UPDATE;
            region = new Region(profileResponseDto.getRegion(), 0, 0); // 지역 입력 방식은 별도로 처리 필요
            // 기존 프로필이 있는 경우 업데이트
            profile = user.getProfile();
            profile.updateProfile(MBTI.valueOf(profileResponseDto.getMbti()), region, profileResponseDto.getMajor(),
                    profileResponseDto.getPlan(), profileResponseDto.getNiceExperience(),
                    hobbies, profileResponseDto.getProfileImage());
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

    // 유저 Profile_Request 요청 조회
//    public List<ProfilePageProfileRequestDto> getProfileRequests(Long adminId) {
//        User admin = userRepository.findById(adminId);
//        if (admin.getRole() != Role.ADMIN) {
//            System.out.println();
//            throw new IllegalStateException("접근 권한 없음");
//        }
//        List<ProfileRequest> profileRequests = profileRequestRepository.findAllByRequestStatus(RequestStatus.PENDING);
//
//        for (ProfileRequest request : profileRequests) {
//            System.out.println(request);
//        }
//
//        // DTO로 변환
//        List<ProfilePageProfileRequestDto> profileRequestDTOs = profileRequests.stream()
//                .map(request -> new ProfilePageProfileRequestDto(
//                        request.getProfile().getMajor(),
//                        request.getProfile().getMbti().name(),
//                        request.getProfile().getRegion().getName(),
//                        request.getProfile().getPlan(),
//                        request.getProfile().getNiceExperience(),
//                        request.getProfile().getImage(),
//                        request.getProfile().getHobbies(),
//                        request.getRequestType(),
//                        request.getRequestStatus()
//                ))
//                .collect(Collectors.toList());
//
//        return profileRequestDTOs;
//
//    }

    public List<String> parseJsonArray(String jsonArray) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonArray, new TypeReference<List<String>>(){});
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

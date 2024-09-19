package com.uplus.miniproject2.dto;

import com.uplus.miniproject2.entity.hobby.Hobby;
import com.uplus.miniproject2.entity.proflie.RequestStatus;
import com.uplus.miniproject2.entity.proflie.RequestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class ProfilePageProfileResponseDto {
    private String major;
    private String mbti;
    private String region;
    private String plan;
    private String niceExperience;
    private byte[] profileImage;
    private List<String> hobbies;
}

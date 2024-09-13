package com.uplus.miniproject2.dto;

import com.uplus.miniproject2.entity.hobby.Hobby;
import com.uplus.miniproject2.entity.proflie.Profile;
import com.uplus.miniproject2.entity.proflie.RequestStatus;
import com.uplus.miniproject2.entity.proflie.RequestType;
import com.uplus.miniproject2.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProfilePageProfileRequestDto {
    private String major;
    private String mbti;
    private String region;
    private String plan;
    private String niceExperience;
    private byte[] profileImage;
    private List<Hobby> hobbies;
    private RequestType requestType;
    private RequestStatus requestStatus;
}

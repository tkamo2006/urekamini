package com.uplus.miniproject2.dto;

import com.uplus.miniproject2.entity.proflie.Profile;
import com.uplus.miniproject2.entity.proflie.RequestStatus;
import com.uplus.miniproject2.entity.proflie.RequestType;
import com.uplus.miniproject2.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileRequestDto {

    private Long profileRequestId;

    private Long userId;

    private String userName;

    private Long profileId;

    private String requestTypeCodeKey;

    private String requestStatusCodeKey;

}

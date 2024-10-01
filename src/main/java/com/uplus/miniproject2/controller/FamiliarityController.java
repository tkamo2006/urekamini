package com.uplus.miniproject2.controller;

import com.uplus.miniproject2.dto.FamiliarityResultDto;
import com.uplus.miniproject2.entity.user.CustomUserDetails;
import com.uplus.miniproject2.service.FamiliarityService;
import com.uplus.miniproject2.util.ApiUtil;
import com.uplus.miniproject2.util.ApiUtil.ApiSuccess;
import java.nio.file.attribute.UserPrincipal;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/familiarity")
public class FamiliarityController {

    private final FamiliarityService familiarityService;

    @GetMapping("/{clickedUserId}")
    public ApiSuccess<?> calculateNameMbtiFamiliarity(
            @AuthenticationPrincipal CustomUserDetails loginUser,
                                                      @PathVariable("clickedUserId") Long clickedUserId) {
        Long loginUserId = loginUser.getId();

        FamiliarityResultDto familiarityResultDto = familiarityService.calculateFamiliarity(loginUserId,
                clickedUserId);


        return ApiUtil.success(familiarityResultDto);
    }
}

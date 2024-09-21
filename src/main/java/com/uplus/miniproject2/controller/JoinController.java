package com.uplus.miniproject2.controller;

import com.uplus.miniproject2.dto.JoinDto;
import com.uplus.miniproject2.service.JoinService;
import com.uplus.miniproject2.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/join")
public class JoinController {

    private final JoinService joinService;

    @PostMapping
    public String joinProcess(@RequestBody JoinDto joinDto) {
        log.info("joinProcess joinDto: {}", joinDto);
        joinService.join(joinDto);

        return "회원가입이 완료되었습니다.";
    }

    @GetMapping("/usernames/{username}")
    public ApiUtil.ApiSuccess<Boolean> joinProcess(@PathVariable String username) {

        return ApiUtil.success(joinService.existsByUsername(username));
    }
}

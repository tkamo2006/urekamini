package com.uplus.miniproject2.controller;

import com.uplus.miniproject2.dto.JoinDto;
import com.uplus.miniproject2.service.JoinService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
@Slf4j
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDto joinDto) {
        log.info("joinProcess joinDto: {}", joinDto);
        joinService.join(joinDto);

        return "회원가입이 완료되었습니다.";
    }


}

package com.uplus.miniproject2.service;

import com.uplus.miniproject2.dto.JoinDto;
import com.uplus.miniproject2.entity.user.Role;
import com.uplus.miniproject2.entity.user.User;
import com.uplus.miniproject2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(JoinDto joinDto) {
        String name = joinDto.getName();
        String username = joinDto.getUsername();
        String password = joinDto.getPassword();
        Role role = joinDto.getRole();
        String gender = joinDto.getGender();


        if(!userRepository.existsByUsername(username)) {
            log.info("성공");
            User user = User.builder().name(name).username(username).password(bCryptPasswordEncoder.encode(password)).role(role).gender(gender).build();
            userRepository.save(user);
        }
    }
}

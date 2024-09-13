package com.uplus.miniproject2.service;

import com.uplus.miniproject2.entity.user.CustomUserDetails;
import com.uplus.miniproject2.entity.user.User;
import com.uplus.miniproject2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new CustomUserDetails(user);
        }

        return null;
    }
}

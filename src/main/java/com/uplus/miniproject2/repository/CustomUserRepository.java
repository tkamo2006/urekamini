package com.uplus.miniproject2.repository;

import com.uplus.miniproject2.dto.MainPageUserDto;
import com.uplus.miniproject2.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomUserRepository {
    Page<MainPageUserDto> findBySearchCondition(String name, String mbti, String major, String gender,
                                                Pageable pageable);

    User findById(Long userId);
}

package com.uplus.miniproject2.repository;

import com.uplus.miniproject2.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);

    User findByUsername(String username);

}

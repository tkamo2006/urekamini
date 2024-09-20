package com.uplus.miniproject2.repository;

import com.uplus.miniproject2.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.profile p LEFT JOIN FETCH p.hobbies WHERE u.id = :userId")
    Optional<User> findByIdWithProfileAndHobbies(Long userId);

    Boolean existsByUsername(String username);

    User findByUsername(String username);

    // 성별 수 카운트
    long countByGender(String gender);

}

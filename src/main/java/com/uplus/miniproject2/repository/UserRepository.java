package com.uplus.miniproject2.repository;

import com.uplus.miniproject2.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.profile p JOIN FETCH p.hobbies WHERE u.id = :userId")
    Optional<User> findByIdWithProfileAndHobbies(Long userId);
}

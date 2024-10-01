package com.uplus.miniproject2.repository;

import com.uplus.miniproject2.entity.proflie.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MapRepository extends JpaRepository<Profile, Long> {
    // 특정 사용자 ID로 프로필 조회
    Profile findByUserId(Long userId);
    @Query("SELECT p FROM Profile p "
            + "JOIN FETCH p.user "
            + "JOIN FETCH p.region "
            + "JOIN ProfileRequest pr ON pr.profile = p "
            + "WHERE pr.requestStatusCodeKey NOT IN ('A02-020', 'A02-030')")
    List<Profile> findAll();
}

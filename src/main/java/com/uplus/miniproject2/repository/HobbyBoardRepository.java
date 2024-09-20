package com.uplus.miniproject2.repository;

import com.uplus.miniproject2.entity.hobby.HobbyBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HobbyBoardRepository extends JpaRepository<HobbyBoard, Long> {

    @Query("SELECT hb FROM HobbyBoard hb JOIN FETCH hb.user u LEFT JOIN FETCH u.profile p")
    Page<HobbyBoard> findAllWithUserAndProfile(Pageable pageable);
}

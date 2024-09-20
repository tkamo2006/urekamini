package com.uplus.miniproject2.repository;

import com.uplus.miniproject2.entity.hobby.HobbyBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyBoardRepository extends JpaRepository<HobbyBoard, Long> {
}

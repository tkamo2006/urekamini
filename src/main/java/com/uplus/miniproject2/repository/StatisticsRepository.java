package com.uplus.miniproject2.repository;

import com.uplus.miniproject2.entity.proflie.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Profile, Long> {
}
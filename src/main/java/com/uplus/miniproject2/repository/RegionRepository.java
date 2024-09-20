package com.uplus.miniproject2.repository;

import com.uplus.miniproject2.entity.proflie.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {

    Region findByName(String name);
}

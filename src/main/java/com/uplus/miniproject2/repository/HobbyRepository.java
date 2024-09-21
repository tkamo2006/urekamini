package com.uplus.miniproject2.repository;

import com.uplus.miniproject2.entity.hobby.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
    Hobby findByName(String name);

    List<Hobby> findAllByNameIn(List<String> hobbies);
}
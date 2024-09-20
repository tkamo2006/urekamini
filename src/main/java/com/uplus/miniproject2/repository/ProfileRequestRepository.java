package com.uplus.miniproject2.repository;

import com.uplus.miniproject2.entity.proflie.ProfileRequest;
import com.uplus.miniproject2.entity.proflie.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRequestRepository extends JpaRepository<ProfileRequest, Long> {
    List<ProfileRequest> findAllByRequestStatus(RequestStatus requestStatus);

    Optional<ProfileRequest> findByUserId(Long userId);
}

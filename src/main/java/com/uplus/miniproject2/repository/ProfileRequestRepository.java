package com.uplus.miniproject2.repository;



import com.uplus.miniproject2.entity.proflie.ProfileRequest;
import com.uplus.miniproject2.entity.proflie.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRequestRepository extends JpaRepository<ProfileRequest, Long> {

    List<ProfileRequest> findAllByRequestStatus(RequestStatus requestStatus);

    List<ProfileRequest> findByUserId(Long userId);

    Page<ProfileRequest> findAll(Pageable pageable);
}

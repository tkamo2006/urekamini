package com.uplus.miniproject2.repository;



import com.uplus.miniproject2.dto.ProfileRequestDto;
import com.uplus.miniproject2.entity.proflie.ProfileRequest;
import com.uplus.miniproject2.entity.proflie.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfileRequestRepository extends JpaRepository<ProfileRequest, Long> {

    List<ProfileRequest> findAllByRequestStatus(RequestStatus requestStatus);

    Optional<ProfileRequest> findByUserId(Long userId);

//    @Query("SELECT new com.uplus.miniproject2.dto.ProfileRequestDto(pr.id, u.id, u.username, p.id, " +
//            "CAST(pr.requestType AS string), CAST(pr.requestStatus AS string)) " +
//            "FROM ProfileRequest pr " +
//            "JOIN pr.user u " +
//            "JOIN pr.profile p " +
//            "ORDER BY CASE WHEN pr.requestStatus = 'PENDING' THEN 0 ELSE 1 END, pr.id DESC"
//    )
//
//    Page<ProfileRequestDto> findAllDto(Pageable pageable);

}
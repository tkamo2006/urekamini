package com.uplus.miniproject2.repository;


import com.uplus.miniproject2.entity.common.Code;
import com.uplus.miniproject2.entity.common.Key.CodeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, CodeKey> {
    @Query("select c.codeKey from Code c where c.codeName = :codeName")
    Optional<CodeKey> findCodeKeyByCodeName(String codeName);

    Code findByCodeKey(CodeKey codeKey);
}

package com.uplus.miniproject2.entity.common;

import com.uplus.miniproject2.entity.common.Key.CodeKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
public class Code {

    @EmbeddedId
    CodeKey codeKey;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "code_name_brief")
    private String codeNameBrief;  // 코드 설명 description

    @Column(name = "order_no")
    private int orderNo; // order 로 하면 예약어로 오류 발생

    @Column(name = "register_date")
    private LocalDateTime register_date; // 등록일

    @Column(name = "update_date")
    private LocalDateTime update_date; //
}

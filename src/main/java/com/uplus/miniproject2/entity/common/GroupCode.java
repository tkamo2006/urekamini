package com.uplus.miniproject2.entity.common;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/*
@Id 에 GeneratedValue 값을 주지 않으면 select 가 2회 수행
Hibernate: select gc1_0.group_code,gc1_0.group_code_desc,gc1_0.group_code_name from group_code gc1_0 where gc1_0.group_code=?
GroupCode(groupCode=012, groupCodeName=취소사유, groupCodeDesc=주문 취소의 사유 구분)
Hibernate: select gc1_0.group_code,gc1_0.group_code_desc,gc1_0.group_code_name from group_code gc1_0 where gc1_0.group_code=?
Hibernate: insert into group_code (group_code_desc,group_code_name,group_code) values (?,?,?)
 */
@Data
@Entity
@Table(name = "group_code")
public class GroupCode {

    @Id
    @Column(name = "group_code")
    private String groupCode;

    @Column(name = "group_code_name")
    private String groupCodeName;

    @Column(name = "group_code_desc")
    private String groupCodeDesc;
}

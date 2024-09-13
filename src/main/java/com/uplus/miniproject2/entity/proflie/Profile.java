package com.uplus.miniproject2.entity.proflie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uplus.miniproject2.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "profile","user"}) // 사용자 데이터 직렬화, 무한 루프 가능성방지, 필요한 데이터만 찾기 위해사용
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"}) // 지역 데이터 직렬화
    private Region region;

    private String hobby;
    private String plan;

    @Lob
    private byte[] image;

    @Builder
    public Profile(User user, Region region, String hobby, String plan, byte[] image) {
        this.user = user;
        this.region = region;
        this.hobby = hobby;
        this.plan = plan;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Region getRegion() {
        return region;
    }

    public String getHobby() {
        return hobby;
    }

    public String getPlan() {
        return plan;
    }

    public byte[] getImage() {
        return image;
    }
}

package com.uplus.miniproject2.entity.proflie;

import com.uplus.miniproject2.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private MBTI mbti;

    private String region;
    private String hobby;
    private String plan;

    @Lob
    private byte[] image;

    @Builder
    public Profile(User user, MBTI mbti, String region, String hobby, String plan, byte[] image) {
        this.user = user;
        this.mbti = mbti;
        this.region = region;
        this.hobby = hobby;
        this.plan = plan;
        this.image = image;
    }
}

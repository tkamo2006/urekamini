package com.uplus.miniproject2.entity.proflie;

import com.uplus.miniproject2.entity.hobby.Hobby;
import com.uplus.miniproject2.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    private String major;
    private String plan;

    @ManyToMany
    @JoinTable(
            name = "profile_hobby",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id")
    )
    private List<Hobby> hobbies = new ArrayList<>();

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    @Builder
    public Profile(User user, MBTI mbti, Region region, String major, String plan, List<Hobby> hobbies, byte[] image) {
        this.user = user;
        this.mbti = mbti;
        this.region = region;
        this.major = major;
        this.plan = plan;
        this.hobbies = hobbies;
        this.image = image;
    }

    public void updateImage(byte[] imageData) {
        this.image = imageData;
    }
}

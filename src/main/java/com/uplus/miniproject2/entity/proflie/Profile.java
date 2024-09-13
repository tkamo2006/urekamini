package com.uplus.miniproject2.entity.proflie;

import com.uplus.miniproject2.entity.hobby.Hobby;
import com.uplus.miniproject2.entity.user.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "region_id")
    private Region region;

    private String major;

    private String plan;

    private String niceExperience;

    @ManyToMany(cascade = CascadeType.PERSIST)
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

    public Profile(User user, MBTI mbti, Region region, String major, String plan, String niceExperience, List<Hobby> hobbies, byte[] image) {
        this.user = user;
        this.mbti = mbti;
        this.region = region;
        this.major = major;
        this.plan = plan;
        this.niceExperience = niceExperience;
        this.hobbies = hobbies;
        this.image = image;
    }

    public void updateImage(byte[] imageData) {
        this.image = imageData;
    }

    public void updateProfile(MBTI mbti, Region region, String major, String plan, String niceExperience, List<Hobby> hobbies, byte[] image) {
        this.mbti = mbti;
        this.region = region;
        this.major = major;
        this.plan = plan;
        this.niceExperience = niceExperience;
        this.hobbies = hobbies;
        this.image = image;
    }
}

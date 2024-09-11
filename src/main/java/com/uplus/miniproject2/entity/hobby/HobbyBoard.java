package com.uplus.miniproject2.entity.hobby;

import com.uplus.miniproject2.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hobby_board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HobbyBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String description;
    private String videoLink;
    private int thumbsUp;

    @Builder
    public HobbyBoard(User user, String title, String description, String videoLink, int thumbsUp) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.videoLink = videoLink;
        this.thumbsUp = thumbsUp;
    }
}

package com.uplus.miniproject2.entity.proflie;

import com.uplus.miniproject2.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profile_request")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @Builder
    public ProfileRequest(User user, Profile profile, RequestType requestType, RequestStatus requestStatus) {
        this.user = user;
        this.profile = profile;
        this.requestType = requestType;
        this.requestStatus = requestStatus;
    }
}

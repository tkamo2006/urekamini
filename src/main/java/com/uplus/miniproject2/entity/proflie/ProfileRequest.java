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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile_request")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Profile getProfile() {
        return profile;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }
}

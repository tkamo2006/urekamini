package com.uplus.miniproject2.entity.hobby;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hobby")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private HobbyCategory hobbyCategory;

    @Builder
    public Hobby(String name) {
        this.name = name;
    }
}

package com.efub.cafetimes.domain;

import com.efub.cafetimes.config.Authority;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="user")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private Long kakaoId;

    @Column
    @NotNull
    private String nickname;

    @Column
    @NotNull
    private String email;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column
    @NotNull
    private String role;

    @Builder
    public User(Long kakaoId, String nickname, String email, String image, Authority authority){
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.email = email;
        this.image = image;
        this.role = authority.getValue();
    }

    public User upadate(String nickname, String image){
        this.nickname = nickname;
        this.image = image;

        return this;
    }

    public void updateRole(String role){
        this.role = role;
    }


}

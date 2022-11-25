package com.efub.cafetimes.domain;

import com.efub.cafetimes.config.Authority;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="user")
public class User extends BaseEntity {

    @Id @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column
    private String password;

    @Builder
    public User(Long kakaoId, String nickname, String email, String password, String image, Authority authority){
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.image = image;
        this.role = authority.getValue();
    }

    public User update(String nickname, String image){
        this.nickname = nickname;
        this.image = image;

        return this;
    }

    public void updateRole(Authority authority){
        this.role = authority.getValue();

    }
}

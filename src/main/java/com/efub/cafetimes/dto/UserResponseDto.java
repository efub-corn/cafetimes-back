package com.efub.cafetimes.dto;

import com.efub.cafetimes.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String nickname;
    private String email;
    private String imageUrl;
    private String role;

    @Builder
    public UserResponseDto(User user){
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.imageUrl = user.getImage();
        this.role = user.getRole();
    }
}

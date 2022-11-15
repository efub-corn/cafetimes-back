package com.efub.cafetimes.service;

import com.efub.cafetimes.domain.User;
import com.efub.cafetimes.dto.UserResponseDto;
import com.efub.cafetimes.repository.UserRepository;
import com.efub.cafetimes.util.errorutil.CustomException;
import com.efub.cafetimes.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto findUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return new UserResponseDto(user);
    }

    public void deleteUser(Long userId){
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        userRepository.deleteById(userId);
    }
}

package com.efub.cafetimes.service;

import com.efub.cafetimes.config.JwtTokenProvider;
import com.efub.cafetimes.domain.User;
import com.efub.cafetimes.dto.RefreshTokenResponseDto;
import com.efub.cafetimes.repository.UserRepository;
import com.efub.cafetimes.util.errorutil.CustomException;
import com.efub.cafetimes.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public void updateRefreshToken(String email, String refreshToken){
        // Redis에 저장 - 만료 시간 설정을 통해 자동 삭제 처리
        User user = userRepository.findUserByEmail(email);
        redisTemplate.opsForValue().set(
                email,
                refreshToken,
                60 * 60 * 24 * 14 * 1000L, //expiration time
                TimeUnit.MILLISECONDS
        );
    }

    @Transactional
    public RefreshTokenResponseDto refreshToken(String email, String refreshToken) {
        User user = userRepository.findUserByEmail(email);

        //refresh token 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String refreshTokenInRedis = redisTemplate.opsForValue().get(user.getEmail());
        if (!refreshToken.equals(refreshTokenInRedis)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        //토큰 재발행 & redis 업데이트
        String newAccessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRole());
        updateRefreshToken(user.getEmail(), newRefreshToken);

        return new RefreshTokenResponseDto(newAccessToken, newRefreshToken);
    }
}

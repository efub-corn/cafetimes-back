package com.efub.cafetimes.repository;

import com.efub.cafetimes.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByKakaoId(Long kakaoId);
    User findByEmail(String email);
    List<User> findUsersByUserIdAndAndDeleteFlagIsFalse(Long userId);
    Optional<User> findUserByUserIdAndDeleteFlagIsFalse(Long userId);
}

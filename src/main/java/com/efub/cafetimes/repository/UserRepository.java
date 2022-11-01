package com.efub.cafetimes.repository;

import com.efub.cafetimes.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByKakaoId(Long kakaoId);
}

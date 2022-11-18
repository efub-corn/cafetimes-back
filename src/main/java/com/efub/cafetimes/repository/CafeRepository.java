package com.efub.cafetimes.repository;

import com.efub.cafetimes.domain.Cafe;
import com.efub.cafetimes.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
    List<Cafe> findByOwner(User user);
}

package com.efub.cafetimes.repository;

import com.efub.cafetimes.domain.Cafe;
import com.efub.cafetimes.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
    Optional<Cafe> findCafeByOwner(User user);
    List<Cafe> findByNameContaining(String q);
    List<Cafe> findCafesByOwner_DeleteFlag(Boolean deleteFlag);

}

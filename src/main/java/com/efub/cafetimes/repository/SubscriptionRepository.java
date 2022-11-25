package com.efub.cafetimes.repository;

import com.efub.cafetimes.domain.Subscription;
import com.efub.cafetimes.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUser(User customer);
}

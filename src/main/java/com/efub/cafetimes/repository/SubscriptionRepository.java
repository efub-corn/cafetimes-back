package com.efub.cafetimes.repository;

import com.efub.cafetimes.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}

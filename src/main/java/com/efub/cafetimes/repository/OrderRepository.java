package com.efub.cafetimes.repository;

import com.efub.cafetimes.domain.Cafe;
import com.efub.cafetimes.domain.Order;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCafeId(Long cafeId);
}

package com.efub.cafetimes.repository;

import com.efub.cafetimes.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCafeId(Long cafeId);
}

package com.efub.cafetimes.repository;

import com.efub.cafetimes.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>, QuerydslPredicateExecutor<Subscription> {

    //쿼리 메소드 이용
    List<Subscription> findByMenu(String menu);
    List<Subscription> findByMenuOrSubscriptionDetail(String menu, String subscriptionDetail);
    List<Subscription> findByPriceLessThan(Integer price);
    List<Subscription> findByPriceLessThanOrderByPriceDesc(Integer price);

    //@Query로 JPQL 이용
    @Query("select i from Subscription i where i.subscriptionDetail like %:subscriptionDetail% order by i.price desc")
    List<Subscription> findBySubscriptionDetail(@Param("subscriptionDetail") String subscriptionDetail);

    @Query(value="select * from subscription i where i.subscription_detail like %:subscriptionDetail% order by i.price desc", nativeQuery = true)
    List<Subscription> findBySubscriptionDetailByNative(@Param("subscriptionDetail") String subscriptionDetail);

}

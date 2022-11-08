package com.efub.cafetimes.repository;

import com.efub.cafetimes.constant.SubscriptionSellStatus;
import com.efub.cafetimes.domain.Subscription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.Column;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class SubscriptionRepositoryTest {

    @Autowired //빈 주입
    SubscriptionRepository subscriptionRepository;

    @Test
    @DisplayName("구독권 저장 테스트")
    public void createSubscriptionTest(){
        Subscription subscription = new Subscription();

        subscription.setMenu("테스트 구독권");
        subscription.setPrice(100000);
        subscription.setSubscriptionDetail("테스트 구독권 상세 설명");
        subscription.setCurrentAmount(33);
        subscription.setTotalAmount(50);
        subscription.setTerm(2);
        subscription.setSubscriptionSellStatus(SubscriptionSellStatus.SELL);
        subscription.setStockNumber(100);
        subscription.setCreatedAt(LocalDateTime.now());
        subscription.setUpdateTime(LocalDateTime.now());
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        System.out.println(savedSubscription.toString());

    }

}
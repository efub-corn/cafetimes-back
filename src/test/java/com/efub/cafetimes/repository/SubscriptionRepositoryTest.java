package com.efub.cafetimes.repository;

import com.efub.cafetimes.constant.SubscriptionSellStatus;
import com.efub.cafetimes.domain.Subscription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.efub.cafetimes.domain.QSubscription;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class SubscriptionRepositoryTest {

    @PersistenceContext
    EntityManager em;

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

    public void createSubscriptionList(){
        for(int i=1; i<=10; i++){
            Subscription subscription = new Subscription();
            subscription.setMenu("테스트 구독권"+i);
            subscription.setPrice(100000+i);
            subscription.setCurrentAmount(33);
            subscription.setTotalAmount(50);
            subscription.setTerm(2);
            subscription.setSubscriptionDetail("테스트 구독권 상세 설명"+i);
            subscription.setSubscriptionSellStatus(SubscriptionSellStatus.SELL);
            subscription.setStockNumber(100);
            subscription.setCreatedAt(LocalDateTime.now());
            subscription.setUpdateTime(LocalDateTime.now());
            Subscription savedSubscription = subscriptionRepository.save(subscription);
        }
    }

    @Test
    @DisplayName("구독권 조회 테스트")
    public void findByMenuTest(){
        this.createSubscriptionList();
        List<Subscription> subscriptionList = subscriptionRepository.findByMenu("테스트 구독권1");
        for(Subscription subscription : subscriptionList){
            System.out.println(subscription.toString());
        }
    }

    @Test
    @DisplayName("구독권명, 구독권상세설명 or 테스트")
    public void findByMenuOrSubscriptionDetailTest(){
        this.createSubscriptionList();
        List<Subscription> subscriptionList = subscriptionRepository.findByMenuOrSubscriptionDetail("테스트 구독권1", "테스트 구독권 상세 설명5");
        for(Subscription subscription : subscriptionList){
            System.out.println(subscription.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest(){
        this.createSubscriptionList();
        List<Subscription> subscriptionList = subscriptionRepository.findByPriceLessThan(100005);
        for(Subscription subscription : subscriptionList){
            System.out.println(subscription.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc(){
        this.createSubscriptionList();
        List<Subscription> subscriptionList = subscriptionRepository.findByPriceLessThanOrderByPriceDesc(100005);
        for(Subscription subscription : subscriptionList){
            System.out.println(subscription.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 구독권 조회 테스트")
    public void findBySubscriptionDetailTest(){
        this.createSubscriptionList();
        List<Subscription> subscriptionList = subscriptionRepository.findBySubscriptionDetail("테스트 구독권 상세 설명");
        for(Subscription subscription : subscriptionList){
            System.out.println(subscription.toString());
        }
    }

    @Test
    @DisplayName("@nativeQuery 속성을 이용한 구독권 조회 테스트")
    public void findBySubscriptionDetailByNative(){
        this.createSubscriptionList();
        List<Subscription> subscriptionList = subscriptionRepository.findBySubscriptionDetailByNative("테스트 구독권 상세 설명");
        for(Subscription subscription : subscriptionList){
            System.out.println(subscription.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest(){
        this.createSubscriptionList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QSubscription qSubscription = QSubscription.subscription;
        JPAQuery<Subscription> query  = queryFactory.selectFrom(qSubscription)
                .where(qSubscription.subscriptionSellStatus.eq(SubscriptionSellStatus.SELL))
                .where(qSubscription.subscriptionDetail.like("%" + "테스트 구독권 상세 설명" + "%"))
                .orderBy(qSubscription.price.desc());

        List<Subscription> subscriptionList = query.fetch();

        for(Subscription subscription : subscriptionList){
            System.out.println(subscription.toString());
        }
    }

    public void createSubscriptionList2(){
        for(int i=1; i<=5; i++){
            Subscription subscription = new Subscription();
            subscription.setMenu("테스트 구독권"+i);
            subscription.setPrice(100000+i);
            subscription.setCurrentAmount(23);
            subscription.setTotalAmount(40);
            subscription.setTerm(2);
            subscription.setSubscriptionDetail("테스트 구독권 상세 설명"+i);
            subscription.setSubscriptionSellStatus(SubscriptionSellStatus.SELL);
            subscription.setStockNumber(100);
            subscription.setCreatedAt(LocalDateTime.now());
            subscription.setUpdateTime(LocalDateTime.now());
            subscriptionRepository.save(subscription);
        }

        for(int i=6; i<=10; i++){
            Subscription subscription = new Subscription();
            subscription.setMenu("테스트 구독권"+i);
            subscription.setPrice(100000+i);
            subscription.setCurrentAmount(23);
            subscription.setTotalAmount(40);
            subscription.setTerm(2);
            subscription.setSubscriptionDetail("테스트 구독권 상세 설명"+i);
            subscription.setSubscriptionSellStatus(SubscriptionSellStatus.SOLD_OUT);
            subscription.setStockNumber(100);
            subscription.setCreatedAt(LocalDateTime.now());
            subscription.setUpdateTime(LocalDateTime.now());
            subscriptionRepository.save(subscription);
        }
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트 2")
    public void queryDslTest2(){
        this.createSubscriptionList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QSubscription subscription = QSubscription.subscription;

        String subscriptionDetail = "테스트 구독권 상세 설명";
        int price = 100003;
        String subscriptionSellStat = "SELL";

        booleanBuilder.and(subscription.subscriptionDetail.like("%" + subscriptionDetail+"%"));
        booleanBuilder.and(subscription.price.gt(price));


    }


}
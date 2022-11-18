package com.efub.cafetimes.repository;

import com.efub.cafetimes.config.Authority;
import com.efub.cafetimes.domain.Cafe;
import com.efub.cafetimes.domain.Order;
import com.efub.cafetimes.domain.Subscription;
import com.efub.cafetimes.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    User owner, customer;
    Cafe cafe;
    Subscription subscription;

    @BeforeEach
    void setup(){
        customer = User.builder().email("customer@efub.com")
                .nickname("customer")
                .authority(Authority.CUSTOMER)
                .build();

        owner = User.builder().email("owner@efub.com")
                .nickname("owner")
                .authority(Authority.OWNER)
                .build();

        cafe = Cafe.builder()
                .cafeName("cafe")
                .owner(owner).build();

        subscription = Subscription.builder()
                .user(customer)
                .cafe(cafe)
                .currentCnt(5)
                .menu("아메리카노")
                .expirationDate(LocalDateTime.MAX)
                .size("L")
                .totalCnt(10)
                .build();
    }

    @Test
    @DisplayName("카페별 주문 리스트 조회 테스트")
    void findOrdersByCafe(){
        //given
        Order order1 = new Order(subscription, cafe.getId(), LocalDateTime.now(), LocalDateTime.now(), true, "얼음 많이 주세요.", false);
        Order order2 = new Order(subscription, cafe.getId(), LocalDateTime.now(), LocalDateTime.now(), true, "얼음 많이 주세요.", false);

        //when
        orderRepository.save(order1);
        orderRepository.save(order2);

        List<Order> orders = orderRepository.findByCafeId(cafe.getId());

        //then
        assertThat(orders.size()).isEqualTo(2);
    }

}
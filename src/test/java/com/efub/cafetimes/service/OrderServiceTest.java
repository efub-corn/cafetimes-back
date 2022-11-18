package com.efub.cafetimes.service;

import com.efub.cafetimes.config.Authority;
import com.efub.cafetimes.domain.Cafe;
import com.efub.cafetimes.domain.Order;
import com.efub.cafetimes.domain.Subscription;
import com.efub.cafetimes.domain.User;
import com.efub.cafetimes.dto.OrderListDto;
import com.efub.cafetimes.dto.OrderRequestDto;
import com.efub.cafetimes.repository.CafeRepository;
import com.efub.cafetimes.repository.OrderRepository;
import com.efub.cafetimes.repository.SubscriptionRepository;
import com.efub.cafetimes.repository.UserRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;
    @Mock
    SubscriptionRepository subscriptionRepository;
    @Mock
    CafeRepository cafeRepository;
    @Mock
    UserRepository userRepository;

    User owner, customer;
    Cafe cafe1, cafe2;
    Subscription subscription;

    @BeforeEach
    void setup () {
        //가짜 객체 주입
        orderService = new OrderService(subscriptionRepository, orderRepository, cafeRepository, userRepository);

        customer = User.builder().email("customer@efub.com")
                .nickname("customer")
                .authority(Authority.CUSTOMER)
                .build();

        owner = User.builder().email("owner@efub.com")
                .nickname("owner")
                .authority(Authority.OWNER)
                .build();

        cafe1 = Cafe.builder()
                .cafeName("cafe1")
                .owner(owner).build();

        cafe2 = Cafe.builder()
                .cafeName("cafe2")
                .owner(owner).build();

        subscription = Subscription.builder()
                .user(customer)
                .cafe(cafe1)
                .currentCnt(5)
                .menu("아메리카노")
                .expirationDate(LocalDateTime.MAX)
                .size("L")
                .totalCnt(10)
                .build();

        ReflectionTestUtils.setField(subscription, "id", 1L);
    }

    @Test
    @DisplayName("구독권 사용해서 주문하기 성공 테스트")
    void order() {
        Order order = new Order(subscription, LocalDateTime.now(), LocalDateTime.now(), true, "얼음 많이 주세요.", false);

        //given
        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .subscriptionId(1L)
                .pickupDate(LocalDateTime.now())
                .pickupTime(LocalDateTime.now())
                .isIce(true)
                .requestInfo("얼음 많이 주세요.")
                .build();


        // mocking
        when(subscriptionRepository.findById(subscription.getId())).thenReturn(Optional.of(subscription));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        //when
        orderService.order(orderRequestDto);

        //then
        assertThat(subscription.getCurrentCnt()).isEqualTo(4);
    }

    @Test
    @DisplayName("사장님이 주문 리스트 확인하기 테스트")
    void findOrders() {
        //given
        List<Order> list = new ArrayList<>();
        List<Cafe> cafes = new ArrayList<>();
        Order order1 = new Order(subscription, LocalDateTime.now(), LocalDateTime.now(), true, "얼음 많이 주세요.", false);
        Order order2 = new Order(subscription, LocalDateTime.now(), LocalDateTime.now(), true, "얼음 많이 주세요.", false);
        list.add(order1);
        list.add(order2);
        cafes.add(cafe1);
        cafes.add(cafe2);

        // mocking
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(cafeRepository.findByOwner(any(User.class))).thenReturn(cafes);
        when(orderRepository.findByCafe(any(Cafe.class))).thenReturn(list);

        //when
        OrderListDto orders = orderService.findOrders(customer.getId());

        //then
        assertThat(orders.getOrderList().size()).isEqualTo(4);
    }
}
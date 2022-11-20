package com.efub.cafetimes.service;

import com.efub.cafetimes.config.Authority;
import com.efub.cafetimes.domain.Cafe;
import com.efub.cafetimes.domain.Event;
import com.efub.cafetimes.domain.Subscription;
import com.efub.cafetimes.domain.User;
import com.efub.cafetimes.dto.EventListDto;
import com.efub.cafetimes.dto.EventRequestDto;
import com.efub.cafetimes.repository.CafeRepository;
import com.efub.cafetimes.repository.EventRepository;
import com.efub.cafetimes.repository.SubscriptionRepository;
import com.efub.cafetimes.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EventServiceTest {

    @InjectMocks
    EventService eventService;

    @Mock
    EventRepository eventRepository;
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
        eventService = new EventService(subscriptionRepository, eventRepository, cafeRepository, userRepository);

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
        Event event = new Event(subscription, cafe1.getId(), LocalDateTime.now(), LocalDateTime.now(), true, "얼음 많이 주세요.", false);

        //given
        EventRequestDto eventRequestDto = EventRequestDto.builder()
                .subscriptionId(1L)
                .pickupDate(LocalDateTime.now())
                .pickupTime(LocalDateTime.now())
                .isIce(true)
                .requestInfo("얼음 많이 주세요.")
                .build();


        // mocking
        when(subscriptionRepository.findById(subscription.getId())).thenReturn(Optional.of(subscription));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        //when
        eventService.order(eventRequestDto);

        //then
        assertThat(subscription.getCurrentCnt()).isEqualTo(4);
    }

    @Test
    @DisplayName("사장님이 주문 리스트 확인하기 테스트")
    void findOrders() {
        //given
        List<Event> list = new ArrayList<>();
        List<Cafe> cafes = new ArrayList<>();
        Event event1 = new Event(subscription, cafe1.getId(), LocalDateTime.now(), LocalDateTime.now(), true, "얼음 많이 주세요.", false);
        Event event2 = new Event(subscription, cafe1.getId(), LocalDateTime.now(), LocalDateTime.now(), true, "얼음 많이 주세요.", false);
        list.add(event1);
        list.add(event2);
        cafes.add(cafe1);
        cafes.add(cafe2);

        // mocking
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(cafeRepository.findByOwner(any(User.class))).thenReturn(cafes);
        when(eventRepository.findByCafeId(cafe1.getId())).thenReturn(list);

        //when
        EventListDto orders = eventService.findOrders(customer.getId());

        //then
        assertThat(orders.getOrderList().size()).isEqualTo(4);
    }
}
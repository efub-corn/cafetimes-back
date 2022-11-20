package com.efub.cafetimes.controller;

import com.efub.cafetimes.config.Authority;
import com.efub.cafetimes.domain.Cafe;
import com.efub.cafetimes.domain.Event;
import com.efub.cafetimes.domain.Subscription;
import com.efub.cafetimes.domain.User;
import com.efub.cafetimes.dto.EventDetailDto;
import com.efub.cafetimes.dto.EventListDto;
import com.efub.cafetimes.dto.EventRequestDto;
import com.efub.cafetimes.dto.EventResponseDto;
import com.efub.cafetimes.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    EventService eventService;

    User owner, customer;
    Cafe cafe1, cafe2;
    Subscription subscription;

    @BeforeEach
    void setup () {
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
    @DisplayName("주문하기 성공")
    void order() throws Exception {
        //given
        EventRequestDto eventRequestDto = new EventRequestDto(1L, 1L, LocalDateTime.now(), LocalDateTime.now(), true, "iceice");

        //when, then
        mvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(eventRequestDto)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }

    @Test
    @DisplayName("사장님 주문 내역 조회하기 성공")
    void orders() throws Exception {
        //given
        Event event = new Event(subscription, cafe1.getId(), LocalDateTime.now(), LocalDateTime.now(), true, "얼음 많이 주세요.", false);

        EventListDto list = new EventListDto(List.of(new EventResponseDto(event)
                , new EventResponseDto(event)));
        Mockito.when(eventService.findOrders(owner.getId())).thenReturn(list);

        //when, then
        mvc.perform(get("/orders").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("cafe1"));
    }

    @Test
    @DisplayName("사장님 주문 내역 세부사항 조회하기 성공")
    void orderDetails() throws Exception {
        //given
        Event event = new Event(subscription, cafe1.getId(), LocalDateTime.now(), LocalDateTime.now(), true, "얼음 많이 주세요.", false);

        EventDetailDto response = new EventDetailDto(true, "ice ice");
        Mockito.when(eventService.findOrderDetail(event.getId())).thenReturn(response);

        //when, then
        mvc.perform(get("/orders/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(true));
    }

    @Test
    @DisplayName("음료 제작 완료 시 주문 호출하기 성공")
    void call() {
    }
}
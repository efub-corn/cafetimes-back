package com.efub.cafetimes.controller;

import com.efub.cafetimes.dto.OrderRequestDto;
import com.efub.cafetimes.dto.OrderResponseDto;
import com.efub.cafetimes.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderResponseDto order(@RequestBody OrderRequestDto orderRequestDto){
        return orderService.order(orderRequestDto);
    }



}

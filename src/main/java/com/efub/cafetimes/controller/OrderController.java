package com.efub.cafetimes.controller;

import com.efub.cafetimes.domain.UserPrincipal;
import com.efub.cafetimes.dto.GeneralResponseDto;
import com.efub.cafetimes.dto.OrderListDto;
import com.efub.cafetimes.dto.OrderRequestDto;
import com.efub.cafetimes.dto.OrderResponseDto;
import com.efub.cafetimes.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    public GeneralResponseDto order(@RequestBody OrderRequestDto orderRequestDto){
        orderService.order(orderRequestDto);
        return new GeneralResponseDto("주문 완료되었습니다.");
    }

    @GetMapping()
    public OrderListDto orders(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return orderService.findOrders(userPrincipal.getId());
    }

}

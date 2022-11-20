package com.efub.cafetimes.controller;

import com.efub.cafetimes.domain.UserPrincipal;
import com.efub.cafetimes.dto.EventDetailDto;
import com.efub.cafetimes.dto.GeneralResponseDto;
import com.efub.cafetimes.dto.EventListDto;
import com.efub.cafetimes.dto.EventRequestDto;
import com.efub.cafetimes.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping()
    public GeneralResponseDto order(@RequestBody EventRequestDto eventRequestDto){
        eventService.order(eventRequestDto);
        return new GeneralResponseDto("주문 완료되었습니다.");
    }

    @GetMapping()
    public EventListDto orders(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return eventService.findOrders(userPrincipal.getId());
    }

    @GetMapping("/{eventId}")
    public EventDetailDto orderDetails(@PathVariable Long eventId){
        return eventService.findOrderDetail(eventId);
    }

    @GetMapping("/{eventId}/call")
    public GeneralResponseDto call(@PathVariable Long eventId){
        eventService.updateEventStatus(eventId);
        return new GeneralResponseDto("음료가 완성되었습니다.");
    }

}

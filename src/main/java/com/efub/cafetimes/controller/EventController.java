package com.efub.cafetimes.controller;

import com.efub.cafetimes.domain.UserPrincipal;
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

    @GetMapping("/{cafeId}")
    public EventListDto events(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return eventService.findOrders(userPrincipal.getId());
    }

}

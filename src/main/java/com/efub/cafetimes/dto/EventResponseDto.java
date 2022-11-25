package com.efub.cafetimes.dto;

import com.efub.cafetimes.domain.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EventResponseDto {
    private String cafeName;
    private String userName;
    private String menu;
    private LocalDateTime pickupTime;

    public EventResponseDto(Event event){
        this.cafeName = event.getSubscription().getCafe().getCafeName();
        this.userName = event.getSubscription().getUser().getNickname();
        this.menu = event.getSubscription().getMenu();
        this.pickupTime = event.getPickupTime();
    }
}

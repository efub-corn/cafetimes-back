package com.efub.cafetimes.dto;

import com.efub.cafetimes.domain.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PickupResponseDto {
    private String cafeName;
    private String menu;
    private LocalDateTime pickupTime;

    public PickupResponseDto(Event event){
        this.cafeName = event.getSubscription().getMenu().getCafe().getCafeName();
        this.menu = event.getSubscription().getMenu().getMenuName();
        this.pickupTime = event.getPickupTime();
    }

}

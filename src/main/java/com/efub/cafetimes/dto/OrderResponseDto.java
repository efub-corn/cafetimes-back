package com.efub.cafetimes.dto;

import com.efub.cafetimes.domain.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private String cafeName;
    private String userName;
    private String menu;
    private LocalDateTime pickupDate;

    public OrderResponseDto(Order order){
        this.cafeName = order.getSubscription().getCafe().getCafeName();
        this.userName = order.getSubscription().getUser().getNickname();
        this.menu = order.getSubscription().getMenu();
        this.pickupDate = order.getPickupDate();
    }
}

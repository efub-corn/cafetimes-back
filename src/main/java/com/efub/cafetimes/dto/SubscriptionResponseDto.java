package com.efub.cafetimes.dto;

import com.efub.cafetimes.domain.Subscription;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SubscriptionResponseDto {
    private String cafeName;
    private String menu;
    private Integer currentCnt;
    private LocalDateTime expirationDate;

    public SubscriptionResponseDto(Subscription subscription){
        this.cafeName = subscription.getMenu().getCafe().getCafeName();
        this.menu = subscription.getMenu().getMenuName();
        this.currentCnt = subscription.getCurrentCnt();
        this.expirationDate = subscription.getExpirationDate();
    }

}

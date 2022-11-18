package com.efub.cafetimes.dto;

import com.efub.cafetimes.domain.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderRequestDto {
    private Long subscriptionId;
    private Long cafeId;
    private LocalDateTime pickupDate;
    private LocalDateTime pickupTime;
    private Boolean isIce;
    private String requestInfo;

    @Builder
    public OrderRequestDto(Long subscriptionId, Long cafeId, LocalDateTime pickupDate, LocalDateTime pickupTime, Boolean isIce, String requestInfo){
        this.subscriptionId = subscriptionId;
        this.cafeId = cafeId;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
        this.isIce = isIce;
        this.requestInfo = requestInfo;
    }
}

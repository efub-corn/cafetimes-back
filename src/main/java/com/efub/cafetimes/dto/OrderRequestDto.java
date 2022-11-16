package com.efub.cafetimes.dto;

import com.efub.cafetimes.domain.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderRequestDto {
    private Long subscriptionId;
    private LocalDateTime pickupDate;
    private LocalDateTime pickupTime;
    private Boolean isIce;
    private String requestInfo;

}

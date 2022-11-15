package com.efub.cafetimes.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SubscriptionResponseDto {
    private String name;
    private String menu;
    private Integer current_cnt;
    private LocalDateTime expiration_date;

}

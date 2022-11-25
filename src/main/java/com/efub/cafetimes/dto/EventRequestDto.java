package com.efub.cafetimes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EventRequestDto {
    private Long subscriptionId;
    private Long cafeId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime pickupTime;
    private Boolean isIce;
    private String requestInfo;

    @Builder
    public EventRequestDto(Long subscriptionId, Long cafeId, LocalDateTime pickupTime, Boolean isIce, String requestInfo){
        this.subscriptionId = subscriptionId;
        this.cafeId = cafeId;
        this.pickupTime = pickupTime;
        this.isIce = isIce;
        this.requestInfo = requestInfo;
    }
}

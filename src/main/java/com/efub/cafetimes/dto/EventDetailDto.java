package com.efub.cafetimes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EventDetailDto {
    private Boolean isIce;
    private String requestInfo;

    public EventDetailDto(Boolean isIce, String requestInfo){
        this.isIce = isIce;
        this.requestInfo = requestInfo;
    }
}

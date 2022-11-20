package com.efub.cafetimes.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EventDetailDto {
    private Boolean isIce;
    private String requestInfo;

    public EventDetailDto(Boolean isIce, String requestInfo){
        this.isIce = isIce;
        this.requestInfo = requestInfo;
    }
}

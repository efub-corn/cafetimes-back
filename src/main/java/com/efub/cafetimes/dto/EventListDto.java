package com.efub.cafetimes.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class EventListDto {
    private List<EventResponseDto> orderList;

    @Builder
    public EventListDto(List<EventResponseDto> orderList){
        this.orderList = orderList;
    }
}

package com.efub.cafetimes.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class OrderListDto {
    private List<OrderResponseDto> orderList;

    @Builder
    public OrderListDto(List<OrderResponseDto> orderList){
        this.orderList = orderList;
    }
}

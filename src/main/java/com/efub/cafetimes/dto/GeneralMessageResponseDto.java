package com.efub.cafetimes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GeneralMessageResponseDto {
    String message;

    public GeneralMessageResponseDto(String message){
        this.message = message;
    }
}

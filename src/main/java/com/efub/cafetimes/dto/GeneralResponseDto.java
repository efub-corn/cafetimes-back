package com.efub.cafetimes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GeneralResponseDto {
    private String message;
    public GeneralResponseDto(String message){
        this.message = message;
    }
}

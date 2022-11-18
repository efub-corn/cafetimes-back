package com.efub.cafetimes.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GeneralResponseDto {
    private String message;

    public GeneralResponseDto(String message){
        this.message = message;
    }
}

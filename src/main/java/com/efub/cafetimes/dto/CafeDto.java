package com.efub.cafetimes.dto;

import com.efub.cafetimes.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CafeDto {
    private Long cafeId;
    private User owner;
    private String cafeName;
    private String address;
    private String businessInfo;
    private String image;
    private Boolean isConfirmed;
    private Integer subscriptionCount;
}

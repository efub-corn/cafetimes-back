package com.efub.cafetimes.dto;

import lombok.*;

import javax.persistence.criteria.CriteriaBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CafeRequestDto {;
    private String cafeName;;
    private String address;
    private String businessInfo;
    private String image;
    private Integer subscriptionCount;

    @Builder
    public CafeRequestDto(String cafeName, String address, String businessInfo, String image, Integer subscriptionCount) {
        this.cafeName = cafeName;
        this.address = address;
        this.businessInfo = businessInfo;
        this.image = image;
        this.subscriptionCount = subscriptionCount;
    }
}

package com.efub.cafetimes.dto;

import com.efub.cafetimes.domain.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class StoreDto {
    private Long storeId;
    private User owner;
    private String storeName;
    private String address;
    private String businessInfo;
    private String image;
    private Boolean isConfirmed;
    private Integer subscriptionCount;
}

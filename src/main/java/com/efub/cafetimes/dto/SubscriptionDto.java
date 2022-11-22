package com.efub.cafetimes.dto;

import com.efub.cafetimes.constant.SubscriptionSellStatus;
import com.efub.cafetimes.domain.Cafe;
import com.efub.cafetimes.domain.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class SubscriptionDto {
    private Long subscriptionId; //구독권 코드
    private Cafe cafe;
    private String menu;
    private Integer price;
    private Integer currentAmount; //현재 구독권 수량
    private Integer totalAmount; //총 구독권 수량
    private Integer term; //구독 기한(month 단위)
    private String subscriptionDetail;
    //private String sellStatCd;
    private LocalDateTime createdAt;
    private LocalDateTime updateTime; //수정 시간
    private User owner;

}

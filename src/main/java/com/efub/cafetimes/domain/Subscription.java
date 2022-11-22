package com.efub.cafetimes.domain;

import com.efub.cafetimes.constant.SubscriptionSellStatus;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name="subscription")
@Getter
@Setter
@ToString
public class Subscription {

    @Id
    @Column(name="subscription_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subscriptionId; //구독권 코드

    @ManyToOne
    @NotNull
    @JoinColumn(name = "userId")
    private User owner;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "cafeId")
    private Cafe cafe;

    @Column(nullable = false, length = 50)
    private String menu; //구독권명

    @Column(name = "price", nullable = false)
    private Integer price; //가격

    @Column(nullable = false)
    private Integer currentAmount; //현재 구독권 수량

    @Column(nullable = false)
    private Integer totalAmount; //총 구독권 수량

    @Column(nullable = false)
    private Integer term; //구독 기한(month 단위)

    @Lob
    @Column(nullable = false)
    private String subscriptionDetail; //구독권 상세 설명

    /*@Enumerated(EnumType.STRING)
    private SubscriptionSellStatus subscriptionSellStatus; //구독권 판매 상태*/

    private LocalDateTime createdAt; //구독 날짜

    private LocalDateTime updateTime; //수정 시간

}

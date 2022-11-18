package com.efub.cafetimes.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="order")
public class Order {
    @Id
    @Column(name="order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name="subscription_id")
    private Subscription subscription;

    @Column
    private Long cafeId;

    @Column
    @NotNull
    private LocalDateTime pickupDate;

    @Column
    @NotNull
    private LocalDateTime pickupTime;

    @Column
    private Boolean isIce;

    @Column(columnDefinition = "TEXT")
    private String requestInfo;

    @Column
    private Boolean isDone;

    @Builder
    public Order(Subscription subscription, Long cafeId, LocalDateTime pickupDate, LocalDateTime pickupTime, Boolean isIce, String requestInfo, Boolean isDone){
        this.subscription = subscription;
        this.cafeId = cafeId;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
        this.isIce = isIce;
        this.requestInfo = requestInfo;
        this.isDone = isDone;
    }

    @PrePersist
    public void setDefaultValues(){
        this.isDone = this.isDone == null ? false : this.isDone;
    }
}

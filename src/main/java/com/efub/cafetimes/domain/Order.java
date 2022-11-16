package com.efub.cafetimes.domain;

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
    @NonNull
    @JoinColumn(name="subscription_id")
    private Subscription subscription;

    @Column
    @NonNull
    private LocalDateTime pickupDate;

    @Column
    private Boolean isIce;

    @Column
    private Boolean hasMilk;

    @Column
    private Boolean hasCream;

    @Column
    private Boolean isDone;

    @Builder
    public Order(Subscription subscription, LocalDateTime pickupDate, Boolean isIce, Boolean hasMilk, Boolean hasCream, Boolean isDone){
        this.subscription = subscription;
        this.pickupDate = pickupDate;
        this.isIce = isIce;
        this.hasMilk = hasMilk;
        this.hasCream = hasCream;
        this.isDone = isDone;
    }

    @PrePersist
    public void setDefaultValues(){
        this.isDone = this.isDone == null ? false : this.isDone;
    }
}

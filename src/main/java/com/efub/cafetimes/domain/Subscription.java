package com.efub.cafetimes.domain;

import com.efub.cafetimes.util.errorutil.CustomException;
import com.efub.cafetimes.util.errorutil.ErrorCode;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="subscription")
public class Subscription {
    @Id @Column(name="subscription_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @NotNull
    @JoinColumn(name="menu_id")
    private Menu menu;

    @NotNull
    @Column
    private String size;

    @NotNull
    @Column
    private Integer currentCnt;

    @NotNull
    @Column
    private Integer totalCnt;

    @NotNull
    @Column
    private LocalDateTime expirationDate; //구독 만료 날짜 = 구독 시작 날짜 + 구독 기한

    @Builder
    public Subscription(User user, Menu menu, String size, Integer currentCnt, Integer totalCnt, LocalDateTime expirationDate){
        this.user = user;
        this.menu = menu;
        this.size = size;
        this.currentCnt = currentCnt;
        this.totalCnt = totalCnt;
        this.expirationDate = expirationDate;
    }

    public void minusCurrentCnt(Integer currentCnt){
        if(currentCnt <= 0){
            throw new CustomException(ErrorCode.INVALID_USE_OF_SUBSCRIPTION);
        }
        this.currentCnt = currentCnt - 1;
    }

}

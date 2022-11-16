package com.efub.cafetimes.service;

import com.efub.cafetimes.domain.Order;
import com.efub.cafetimes.domain.Subscription;
import com.efub.cafetimes.dto.OrderRequestDto;
import com.efub.cafetimes.dto.OrderResponseDto;
import com.efub.cafetimes.repository.OrderRepository;
import com.efub.cafetimes.repository.SubscriptionRepository;
import com.efub.cafetimes.util.errorutil.CustomException;
import com.efub.cafetimes.util.errorutil.ErrorCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final SubscriptionRepository subscriptionRepository;
    private final OrderRepository orderRepository;

    //주문하기
    public OrderResponseDto order(OrderRequestDto orderRequestDto){
        Order order = orderRepository.save(toOrderEntity(orderRequestDto));
        return new OrderResponseDto(order);
    }

    private Order toOrderEntity(OrderRequestDto orderRequestDto){
        Subscription subscription = findSubscriptionEntity(orderRequestDto.getSubscriptionId());
        return Order.builder()
                .subscription(subscription)
                .pickupDate(orderRequestDto.getPickupDate())
                .pickupTime(orderRequestDto.getPickupTime())
                .isIce(orderRequestDto.getIsIce())
                .requestInfo(orderRequestDto.getRequestInfo())
                .build();
    }

    private Subscription findSubscriptionEntity(Long subscriptionId){
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new CustomException(ErrorCode.SUBSCRIPTION_NOT_FOUND));
    }
}

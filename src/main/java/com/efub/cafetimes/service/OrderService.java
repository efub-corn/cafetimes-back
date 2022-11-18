package com.efub.cafetimes.service;

import com.efub.cafetimes.domain.Cafe;
import com.efub.cafetimes.domain.Order;
import com.efub.cafetimes.domain.Subscription;
import com.efub.cafetimes.domain.User;
import com.efub.cafetimes.dto.OrderListDto;
import com.efub.cafetimes.dto.OrderRequestDto;
import com.efub.cafetimes.dto.OrderResponseDto;
import com.efub.cafetimes.repository.CafeRepository;
import com.efub.cafetimes.repository.OrderRepository;
import com.efub.cafetimes.repository.SubscriptionRepository;
import com.efub.cafetimes.repository.UserRepository;
import com.efub.cafetimes.util.errorutil.CustomException;
import com.efub.cafetimes.util.errorutil.ErrorCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final SubscriptionRepository subscriptionRepository;
    private final OrderRepository orderRepository;
    private final CafeRepository cafeRepository;
    private final UserRepository userRepository;

    //주문하기
    @Transactional
    public void order(OrderRequestDto orderRequestDto){
        //해당 subscription count 차감
        Subscription subscription = findSubscriptionEntity(orderRequestDto.getSubscriptionId());
        subscription.minusCurrentCnt(subscription.getCurrentCnt());

        Order order = orderRepository.save(toOrderEntity(orderRequestDto));
    }

    //사장님이 주문 리스트 조회
    public OrderListDto findOrders(Long userId){
        //사장님의 카페에 해당하는 주문들에 대해서
        List<OrderResponseDto> orders = new ArrayList<>();
        List<Cafe> cafes = cafeRepository.findByOwner(findUserEntity(userId));

        cafes.stream().forEach(
                cafe -> {
                    List<Order> orderList = orderRepository.findByCafe(cafe);
                    for(Order order : orderList) {
                        orders.add(new OrderResponseDto(order));

                    }
                }
        );

        return new OrderListDto(orders);
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

    private User findUserEntity(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}

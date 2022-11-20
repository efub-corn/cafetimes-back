package com.efub.cafetimes.service;

import com.efub.cafetimes.domain.Cafe;
import com.efub.cafetimes.domain.Event;
import com.efub.cafetimes.domain.Subscription;
import com.efub.cafetimes.domain.User;
import com.efub.cafetimes.dto.EventListDto;
import com.efub.cafetimes.dto.EventRequestDto;
import com.efub.cafetimes.dto.EventResponseDto;
import com.efub.cafetimes.repository.CafeRepository;
import com.efub.cafetimes.repository.EventRepository;
import com.efub.cafetimes.repository.SubscriptionRepository;
import com.efub.cafetimes.repository.UserRepository;
import com.efub.cafetimes.util.errorutil.CustomException;
import com.efub.cafetimes.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final SubscriptionRepository subscriptionRepository;
    private final EventRepository eventRepository;
    private final CafeRepository cafeRepository;
    private final UserRepository userRepository;

    //주문하기
    @Transactional
    public void order(EventRequestDto eventRequestDto){
        //해당 subscription count 차감
        Subscription subscription = findSubscriptionEntity(eventRequestDto.getSubscriptionId());
        subscription.minusCurrentCnt(subscription.getCurrentCnt());

        Event event = eventRepository.save(toOrderEntity(eventRequestDto));
    }

    //사장님 카페 별 주문 리스트 조회
    public EventListDto findOrders(Long userId){
        //사장님의 카페에 해당하는 주문들에 대해서
        List<EventResponseDto> events = new ArrayList<>();
        List<Cafe> cafes = cafeRepository.findByOwner(findUserEntity(userId));

        cafes.stream().forEach(
                cafe -> {
                    List<Event> eventList = eventRepository.findByCafeId(cafe.getId());
                    for(Event event : eventList) {
                        events.add(new EventResponseDto(event));

                    }
                }
        );

        return new EventListDto(events);
    }

    private Event toOrderEntity(EventRequestDto eventRequestDto){
        Subscription subscription = findSubscriptionEntity(eventRequestDto.getSubscriptionId());
        return com.efub.cafetimes.domain.Event.builder()
                .subscription(subscription)
                .pickupDate(eventRequestDto.getPickupDate())
                .pickupTime(eventRequestDto.getPickupTime())
                .isIce(eventRequestDto.getIsIce())
                .requestInfo(eventRequestDto.getRequestInfo())
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

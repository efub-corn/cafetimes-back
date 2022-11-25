package com.efub.cafetimes.service;

import com.efub.cafetimes.config.Authority;
import com.efub.cafetimes.domain.Subscription;
import com.efub.cafetimes.domain.User;
import com.efub.cafetimes.dto.PickupResponseDto;
import com.efub.cafetimes.dto.SubscriptionResponseDto;

import com.efub.cafetimes.dto.UserResponseDto;
import com.efub.cafetimes.repository.EventRepository;
import com.efub.cafetimes.repository.SubscriptionRepository;
import com.efub.cafetimes.repository.UserRepository;
import com.efub.cafetimes.util.errorutil.CustomException;
import com.efub.cafetimes.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final EventRepository eventRepository;

    @Transactional
    public UserResponseDto findUser(Long userId){
        User user = findUserEntity(userId);
        return new UserResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long userId){
        findUserEntity(userId);
        userRepository.deleteById(userId);
    }

    @Transactional
    public void switchToOwner(Long userId, MultipartFile file){
        //파일 업로드

        //trainee 전환
        User user = findUserEntity(userId);
        user.updateRole(Authority.TRAINEE); //변경 감지 dirty checking
    }

    public List<SubscriptionResponseDto> findSubscriptionInfo(Long userId){
        //Subscription table에서 user 구독 내용 가져와서 반환
        User user = findUserEntity(userId);
        return subscriptionRepository.findByUser(user)
                .stream()
                .map(
                        subscription -> new SubscriptionResponseDto(subscription)
                )
                .collect(Collectors.toList());
    }

    public List<PickupResponseDto> findPickupList(Long userId){
        List<PickupResponseDto> list = new ArrayList<>();

        User user = findUserEntity(userId);

        List<Subscription> subscriptions = subscriptionRepository.findByUser(user);
        subscriptions.forEach(
                subscription -> {
                    eventRepository.findById(subscription.getId()).ifPresent(
                            event -> list.add(new PickupResponseDto(event))
                    );
                }
        );

        return list;
    }

    public User findUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}

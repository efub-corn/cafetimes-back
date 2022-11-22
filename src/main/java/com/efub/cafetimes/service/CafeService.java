package com.efub.cafetimes.service;

import com.efub.cafetimes.domain.Cafe;
import com.efub.cafetimes.domain.User;
import com.efub.cafetimes.dto.CafeResponseDto;
import com.efub.cafetimes.dto.UserResponseDto;
import com.efub.cafetimes.repository.CafeRepository;
import com.efub.cafetimes.repository.UserRepository;
import com.efub.cafetimes.util.errorutil.CustomException;
import com.efub.cafetimes.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.efub.cafetimes.util.errorutil.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long saveCafe(Long userId, CafeResponseDto cafeResponseDto) throws IOException {
        User owner = userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND, null));
        cafeResponseDto.updateUser(owner);
        Long cafeId = cafeRepository.save(cafeResponseDto.toEnity()).getId();
        return cafeId;
    }

    @Transactional
    public List<CafeResponseDto> getCafeList() {
        List<String> menuUrl = new ArrayList<>();
        List<Cafe> CafeList = cafeRepository.findCafesByOwner_DeleteFlag(false);
        List<CafeResponseDto> cafeResponseDtoList = new ArrayList<>();

        for (Cafe cafe : CafeList) {
            // 주인 찾기
            CafeResponseDto cafeResponseDto = createCafeResponseDto(cafe, menuUrl);
            cafeResponseDtoList.add(cafeResponseDto);

        }
        return cafeResponseDtoList;
    }

    @Transactional
    public List<CafeResponseDto> selectCafeByQuery(String q) {
        List<Cafe> cafes = cafeRepository.findByNameContaining(q);
        List<CafeResponseDto> cafeResponseDtoList = new ArrayList<>();
        for (Cafe cafe : cafes) {
            List <String> menuUrl = new ArrayList<>();
            CafeResponseDto cafeResponseDto = createCafeResponseDto(cafe, menuUrl);
            cafeResponseDtoList.add(cafeResponseDto);
        }
        return cafeResponseDtoList;
    }

    public User findUserByOwner(Cafe cafe) {
        Long userId = cafe.getOwner().getUserId();
        User user = userRepository.findUserByUserIdAndDeleteFlagIsFalse(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, null));
        return user;
    }

    public CafeResponseDto createCafeResponseDto(Cafe cafe, List<String> menuUrl) {
        User user = findUserByOwner(cafe);
        String ownerName = user.getNickname();
        return new CafeResponseDto(cafe, user, ownerName);
    }


}

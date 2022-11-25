package com.efub.cafetimes.controller;

import com.efub.cafetimes.domain.UserPrincipal;
import com.efub.cafetimes.dto.*;
import com.efub.cafetimes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public UserResponseDto userDetails(@PathVariable Long userId){
        return userService.findUser(userId);
    }

    @GetMapping("/account/profile")
    public UserResponseDto userAccountDetails(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userService.findUser(userPrincipal.getId());
    }

    @DeleteMapping("/account")
    public GeneralMessageResponseDto deleteUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        userService.deleteUser(userPrincipal.getId());
        return new GeneralMessageResponseDto("탈퇴 되었습니다.");
    }

    @PostMapping("/switch/owner")
    public GeneralMessageResponseDto switchUser(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam("file") MultipartFile file){
        userService.switchToOwner(userPrincipal.getId(), file);
        return new GeneralMessageResponseDto(("신청 완료되었습니다."));
    }

    @GetMapping("/mySubscription")
    public List<SubscriptionResponseDto> userSubscriptionDetails(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return userService.findSubscriptionInfo(userPrincipal.getId());
    }

    @GetMapping("/pickup")
    public List<PickupResponseDto> userPickuplist(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return userService.findPickupList(userPrincipal.getId());
    }

}

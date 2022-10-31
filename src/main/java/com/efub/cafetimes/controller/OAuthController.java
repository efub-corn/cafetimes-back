package com.efub.cafetimes.controller;

import com.efub.cafetimes.service.OAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OAuthController {
    private final OAuthUserService oAuthUserService;
    @GetMapping("/oauth/kakao")
    public void kakaoCallback(@RequestParam String code){
        //LoginResponseDto loginResponseDto = oAuthUserService.login(code)
    }
}

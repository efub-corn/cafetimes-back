package com.efub.cafetimes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OauthController {
    @GetMapping("/oauth/kakao")
    public void kakaoCallback(@RequestParam String code){
        //LoginResponseDto loginResponseDto = oauthService.login(code)
    }
}

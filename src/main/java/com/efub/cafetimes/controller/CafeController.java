package com.efub.cafetimes.controller;

import com.efub.cafetimes.config.JwtTokenProvider;
import com.efub.cafetimes.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CafeController {
    private final CafeService cafeService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/cafes")
    ResponseEntity<?> cafeList() {
        return new ResponseEntity<>(cafeService.getCafeList(), HttpStatus.OK);
    }
    @GetMapping("/cafes/search")
    ResponseEntity<?> userById(@RequestParam(name = "keyword") String q){
        return new ResponseEntity<>(cafeService.selectCafeByQuery(q), HttpStatus.OK);
    }
    @GetMapping("/cafes/{cafeId}")
    ResponseEntity<?> cafeDetail(@PathVariable("cafeId") Long cafeId) {
        return new ResponseEntity<>(cafeService.getcafeDetail(cafeId), HttpStatus.OK);
    }
    @GetMapping("/cafes/myCafe")
    ResponseEntity<?> myStoreDetail(HttpServletRequest httpRequest) {
        SessionUserDTO sessionUser = jwtTokenProvider.getUserInfoByToken(httpRequest);
        return new ResponseEntity<>(cafeService.getCafeDetailByOwner(sessionUser.getUserId()), HttpStatus.OK);
    }

}

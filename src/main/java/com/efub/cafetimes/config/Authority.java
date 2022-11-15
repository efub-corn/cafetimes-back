package com.efub.cafetimes.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Authority {
    ADMIN("ROLE_ADMIN"),
    CUSTOMER("ROLE_CUSTOMER"),
    OWNER("ROLE_OWNER"),
    TRAINEE("ROLE_TRAINEE");

    private String value;
}

package com.example.security_study.domain.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AuthoritiesEnum {

    ADMIN( "ROLE_ADMIN" , 1 , new AuthenticationMenu[]{ AuthenticationMenu.ROLE_NOTICE , AuthenticationMenu.ROLE_BOARD , AuthenticationMenu.ROLE_POPUP}),
    MANAGER("ROLE_MANAGER"  , 2 , new AuthenticationMenu[]{ AuthenticationMenu.ROLE_BOARD , AuthenticationMenu.ROLE_POPUP}),
    USER("ROLE_USER" , 3 , new AuthenticationMenu[]{});

    private final String role;
    private final int grant_level;
    private final AuthenticationMenu[] options;

    AuthoritiesEnum(String role, int grant_level, AuthenticationMenu[] options) {
        this.role = role;
        this.grant_level = grant_level;
        this.options = options;
    }

    public static AuthoritiesEnum findAuthOptions(AuthenticationMenu option){
        return Arrays.stream(AuthoritiesEnum.values())
                .filter(paymentGroup -> hasAuthOption(paymentGroup , option))
                .findAny()
                .orElse(AuthoritiesEnum.USER);
    }

    private static boolean hasAuthOption(AuthoritiesEnum Authorities , AuthenticationMenu option){
        return Arrays.stream(Authorities.options)
                .anyMatch(match ->  match == option);
    }
}

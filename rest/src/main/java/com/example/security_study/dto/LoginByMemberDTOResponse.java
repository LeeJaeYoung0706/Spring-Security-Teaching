package com.example.security_study.dto;

import com.example.security_study.domain.Member;
import com.example.security_study.domain.enums.AuthoritiesEnum;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class LoginByMemberDTOResponse {

    private long memberId;
    private final AuthoritiesEnum auth;
    private List<GrantedAuthority> grantedAuth;
    private final String token;
    private final String name;

    public LoginByMemberDTOResponse(AuthoritiesEnum auth, String name, String token) {
        this.auth = auth;
        this.token = token;
        this.name = name;
        setGrantedAuth(auth);
    }

    public static LoginByMemberDTOResponse createLoginByMemberDTO(Member member , String token) {
        return new LoginByMemberDTOResponse(
                member.getAuth(),
                member.getName(),
                token
        );
    }

    private void setGrantedAuth(AuthoritiesEnum auth) {
        this.grantedAuth = Arrays.stream(auth.getOptions())
                .map(authenticationMenu -> new SimpleGrantedAuthority(authenticationMenu.name()))
                .collect(Collectors.toList());
    }
}

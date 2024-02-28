package com.example.security_study.domain.auth;

import com.example.security_study.domain.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberDetails implements UserDetails {

    private final long id;
    private final Member member;
    private final String username;
    private final List<GrantedAuthority> authorities;


    @Builder
    private MemberDetails(Member member){
        this.id = member.getId();
        this.member = member;
        this.username = member.getUserId();
        this.authorities = Arrays.stream(member.getAuth().getOptions())
                .map(authorityOptionEnum -> new SimpleGrantedAuthority(authorityOptionEnum.name()))
                .collect(Collectors.toList());
    }

    public static MemberDetails createMemberDetails(Member member){
        return MemberDetails.builder().member(member).build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return member.isEnable();
    }
}


package com.example.security_study.domain;


import com.example.security_study.domain.enums.AuthoritiesEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity implements Serializable {

    @Id
    @Column(name = "member_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String userId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private AuthoritiesEnum auth;
    private boolean enable;

    @Builder
    public Member(long id, String userId, String password, String name, AuthoritiesEnum auth, boolean enable) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.auth = auth;
        this.enable = enable;
    }

    public static Member createMember(String userId , String password, String name, AuthoritiesEnum auth){
        return Member.builder()
                .userId(userId)
                .name(name)
                .password(password)
                .auth(auth)
                .enable(true)
                .build();
    }

}

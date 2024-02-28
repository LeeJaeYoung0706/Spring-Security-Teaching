package com.example.security_study.service;

import com.example.security_study.domain.enums.AuthenticationMenu;
import com.example.security_study.domain.enums.AuthoritiesEnum;
import com.example.security_study.dto.SaveMemberDTO;
import com.example.security_study.lib.Response;
import com.example.security_study.lib.RestException;
import com.example.security_study.mapper.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @Transactional
    @Rollback(value = false)
    public void saveMember() {
        SaveMemberDTO saveMemberDTO = new SaveMemberDTO("TESTUSER", "Wlsdns@@1234", "진운");

        Response<String> stringResponse = memberService.saveMember(saveMemberDTO);
        System.out.println(stringResponse.getData());

//        Assertions.assertThrows(RestException.class, () -> {
//            Response<String> stringResponse = memberService.saveMember(saveMemberDTO);
//            System.out.println(stringResponse.getData());
//        });
    }

    @Test
    public void testEnum () {
        List<AuthenticationMenu> authenticationMenus = Arrays.stream(AuthoritiesEnum.MANAGER.getOptions()).toList();
        for (AuthenticationMenu authenticationMenu : authenticationMenus ) {
            System.out.println(authenticationMenu.name());
        }

    }
}
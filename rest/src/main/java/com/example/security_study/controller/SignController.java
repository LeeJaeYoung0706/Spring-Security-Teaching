package com.example.security_study.controller;

import com.example.security_study.service.MemberService;
import com.example.security_study.dto.SaveMemberDTO;
import com.example.security_study.dto.LoginByMemberDTORequest;
import com.example.security_study.lib.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class SignController  {

    private final MemberService memberService;

    @PostMapping("/signin")
    public Response<?> signInMember(@RequestBody @Valid LoginByMemberDTORequest loginByMemberDTORequest, BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors())
            throw new BindException(bindingResult);
       return Response.builder( 200 , memberService.loginByMember(loginByMemberDTORequest)).build();
    }

    @PostMapping("/save/member")
    public Response<String> saveMember(@RequestBody @Valid SaveMemberDTO saveMemberDTO , BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors())
            throw new BindException(bindingResult);
        return memberService.saveMember(saveMemberDTO);
    }

}

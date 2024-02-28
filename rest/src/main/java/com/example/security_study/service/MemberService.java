package com.example.security_study.service;

import com.example.security_study.domain.Member;
import com.example.security_study.domain.auth.MemberDetails;
import com.example.security_study.dto.LoginByMemberDTORequest;
import com.example.security_study.dto.LoginByMemberDTOResponse;
import com.example.security_study.dto.SaveMemberDTO;
import com.example.security_study.lib.Response;
import com.example.security_study.lib.RestException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


public interface MemberService {

    Response<String> saveMember(SaveMemberDTO memberDTO) throws RestException;
    MemberDetails findByIdTransMemberDetails(Long id) throws RestException;
    LoginByMemberDTOResponse loginByMember(LoginByMemberDTORequest loginByMemberRequest) throws UsernameNotFoundException;
}

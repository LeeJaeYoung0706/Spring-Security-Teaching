package com.example.security_study.service;

import com.example.security_study.domain.Member;
import com.example.security_study.domain.auth.MemberDetails;
import com.example.security_study.domain.enums.AuthoritiesEnum;
import com.example.security_study.dto.LoginByMemberDTORequest;
import com.example.security_study.dto.LoginByMemberDTOResponse;
import com.example.security_study.dto.SaveMemberDTO;
import com.example.security_study.lib.JwtProvider;
import com.example.security_study.lib.Response;
import com.example.security_study.lib.RestException;
import com.example.security_study.mapper.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService , UserDetailsService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public Response<String> saveMember(SaveMemberDTO memberDTO) throws RestException {

        Optional<Member> byMember_userId = memberRepository.findByUserId(memberDTO.getUserId());
        System.out.println(byMember_userId);
        // 아이디 검증
        if(byMember_userId.isPresent()){
            throw RestException.build("이미 존재하는 아이디입니다.", 403);
        }

        Member member = Member.createMember(
                memberDTO.getUserId(),
                bCryptPasswordEncoder.encode(memberDTO.getPassword()),
                memberDTO.getName(),
                AuthoritiesEnum.USER
        );

        Member save = memberRepository.save(member);

        if (Objects.isNull(save)) {
            throw RestException.build("서버 오류 다시 시도해주세요." , 500);
        }

        return Response.builder(200 , "회원 가입 성공").build();
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDetails findByIdTransMemberDetails(Long id) throws RestException {
        return memberRepository.findById(id).map( (member) -> MemberDetails.createMemberDetails(member))
                .orElseThrow( () -> RestException.build("해당 아이디의 회원을 찾을 수 없습니다." , 403));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository
                .findByUserId(username)
                .map( member -> MemberDetails.createMemberDetails(member))
                .orElseThrow( () -> new UsernameNotFoundException("유저 정보를 찾을 수 없습니다."));
    }

    @Override
    @Transactional
    public LoginByMemberDTOResponse loginByMember(LoginByMemberDTORequest loginByMemberRequest) throws UsernameNotFoundException{
        return memberRepository.findByUserId(loginByMemberRequest.getUserId())
                .filter(authority -> bCryptPasswordEncoder
                        .matches(loginByMemberRequest.getPassword() ,
                                authority.getPassword()))
                .map(authority -> {
                    String token = jwtProvider.generateToken(authority.getId()
                            , authority.getAuth().getRole());
                    return LoginByMemberDTOResponse.createLoginByMemberDTO(authority , token);
                })
                .orElseThrow( () -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));

    }


}

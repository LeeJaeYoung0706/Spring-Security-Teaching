package com.example.security_study.filter;

import com.example.security_study.domain.Member;
import com.example.security_study.domain.auth.MemberDetails;
import com.example.security_study.lib.JwtProvider;
import com.example.security_study.lib.URLMatcher;
import com.example.security_study.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final String SPRING_JWT_HEADER;
    private final JwtProvider jwtProvider;
    private MemberService memberService;


    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 크롬 OPTIONS 요청 처리
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // 회원가입 및 로그인 정상 통과
        URLMatcher signMatcher = new URLMatcher(List.of("/auth/**"));
        if (signMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = request.getHeader(SPRING_JWT_HEADER);

        if(token == null){
            System.out.println(SPRING_JWT_HEADER);
            // 토큰이 없다면 Exception 처리
            return;
        }

        try {
            if (!jwtProvider.validateToken(token)) {
                // 만료된 토큰 처리
                return;
            }
        } catch (Exception e) {
            // 유효하지 않은토큰 처리
            return;
        }
        // 인가된 사용자라면?
        if (token != null) {
            // 토큰으로 아이디 정보 찾을 수 있는 메소드
            long memberId = jwtProvider.getMemberId(token);
            MemberDetails member = memberService.findByIdTransMemberDetails(memberId);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( member, null,  member.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}

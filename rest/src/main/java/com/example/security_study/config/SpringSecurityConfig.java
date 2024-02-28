package com.example.security_study.config;

import com.example.security_study.domain.enums.AuthenticationMenu;
import com.example.security_study.domain.enums.AuthoritiesEnum;
import com.example.security_study.filter.SecurityFilter;
import com.example.security_study.lib.JwtProvider;
import com.example.security_study.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;



@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Value("${spring.jwt.header}")
    private String SPRING_JWT_HEADER;

    @Bean
    public JwtProvider getJwtProvider() {
        return new JwtProvider();
    }

    @Bean
    public SecurityFilter getSecurityFilter() {
        return new SecurityFilter(SPRING_JWT_HEADER, getJwtProvider());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST" , "PUT" , "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // 관리자 , 운영자 , 유저 ( 권한별로 level , 1 , 2 , 3) Enum 처리리
    @Bean
    public SecurityFilterChain customFilterChain(HttpSecurity http) throws
            Exception {

        http
                .csrf((csrfConfig) -> csrfConfig.disable())
                .cors( corsConfigurer -> {
                    Customizer.withDefaults();
                })
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( (authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.OPTIONS)
                                .permitAll()
                                .requestMatchers("/auth/signin")
                                .permitAll()
                                .requestMatchers("/test/notice").hasRole("NOTICE")
                                .requestMatchers("/test/board").hasRole("BOARD")
                                .requestMatchers("/test/popup").hasRole("POPUP")
                                .anyRequest().authenticated()
                ).addFilterBefore(getSecurityFilter() , UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * 정적 소스 Filter 거치지 않도록 설정
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        };
    }
}

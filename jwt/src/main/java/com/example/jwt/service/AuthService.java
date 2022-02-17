package com.example.jwt.service;

import com.example.jwt.controller.dto.TokenDto;
import com.example.jwt.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService{

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    //private final RedisTemplate<String, Object> redisTemplate;

    // 로그인 관련
    public String authorize(String email, String pw) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, pw);


        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);


        SecurityContextHolder.getContext().setAuthentication(authentication);


        return tokenProvider.createToken(authentication.getName(), authentication);
    }

    /*public void logout(String accessToken) {
        redisTemplate.opsForValue().set(accessToken,"accessToken",18000);
    }
*/
}

package com.example.jwt.config;

import com.example.jwt.jwt.JwtAccessDeniedHandler;
import com.example.jwt.jwt.JwtAuthenticationEntryPoint;
import com.example.jwt.jwt.JwtSecurityConfig;
import com.example.jwt.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.beans.BeanProperty;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/mariadb/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        http
                .authorizeHttpRequests() // 사용하는 요청들에 대해 접근제한을 설정
                .antMatchers("/member").permitAll() // 해당 패턴에 대한 요청은 인증없이 접근을 허용
                .anyRequest().authenticated(); //나머지 요청들은 인증을 받아야됨
                */
        http
                .csrf().disable() // token 을 사용하기 때문에 csrf disable

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler) // Exception 추가

                .and()
                .headers()
                .frameOptions()
                .sameOrigin() // h2 콘솔관련 설정?

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 설정 stateless

                .and()
                .authorizeRequests()
                .antMatchers("/member").permitAll()
                .anyRequest().authenticated() //

                .and()
                .apply(new JwtSecurityConfig(tokenProvider)); // jwtFilter 를 JwtSecurityConfig 에도 적용

    }

}

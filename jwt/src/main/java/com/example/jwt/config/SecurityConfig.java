package com.example.jwt.config;

import com.example.jwt.jwt.JwtAccessDeniedHandler;
import com.example.jwt.jwt.JwtAuthenticationEntryPoint;
import com.example.jwt.jwt.JwtSecurityConfig;
import com.example.jwt.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
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
                .antMatchers("/mariadb/**", "/favicon.ico", "/css/**", "/templates/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        http
                .authorizeHttpRequests() // ???????????? ???????????? ?????? ??????????????? ??????
                .antMatchers("/member").permitAll() // ?????? ????????? ?????? ????????? ???????????? ????????? ??????
                .anyRequest().authenticated(); //????????? ???????????? ????????? ????????????
                */
        http
                .csrf().disable() // token ??? ???????????? ????????? csrf disable
                .formLogin().loginPage("/login")

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler) // Exception ??????

                .and()
                .headers()
                .frameOptions()
                .sameOrigin() // h2 ???????????? ???????

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // ?????? ?????? stateless

                .and()
                .authorizeRequests()
                .antMatchers("/member/**").permitAll()
                .anyRequest().authenticated() //


                .and()
                .apply(new JwtSecurityConfig(tokenProvider)); // jwtFilter ??? JwtSecurityConfig ?????? ??????

    }

}

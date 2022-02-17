package com.example.jwt.controller;

import com.example.jwt.controller.dto.LoginDto;
import com.example.jwt.controller.dto.MemberDto;
import com.example.jwt.controller.dto.TokenDto;
import com.example.jwt.domain.Member;
import com.example.jwt.jwt.TokenProvider;
import com.example.jwt.service.AuthService;
import com.example.jwt.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/member/api")
@RequiredArgsConstructor
public class MemberApiController {

    private final AuthService authService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder; // Bean 으로 주입을 받아야 사용이 가능함

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        Optional<Member> member = memberService.login(loginDto.getEmail(), loginDto.getPassword());


        if (!member.isPresent()) {

            return ResponseEntity.ok("null");
        }

        String token = authService.authorize(member.get().getEmail(), member.get().getPw());
        System.out.println("token = " + token);


        return ResponseEntity.ok(token);
    }

    @PostMapping("join")
    public ResponseEntity<Long> join(@RequestBody MemberDto memberDto) {
        String encodedPw = passwordEncoder.encode(memberDto.getPassword());

        Long joinMember = memberService.join(Member.createMember(memberDto.getEmail(), encodedPw, "user"));
        return ResponseEntity.ok(joinMember);
    }
}

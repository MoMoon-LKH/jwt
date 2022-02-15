package com.example.jwt.service;

import com.example.jwt.domain.Member;
import com.example.jwt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(Member member) {
        memberRepository.duplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<Member> login(String email, String password) {
        Member member = memberRepository.findOneByEmail(email).get();

        if (!passwordEncoder.matches(password, member.getPw())) {
            System.out.println("password isn't same ");
            return Optional.empty();
        }

        return Optional.of(member);
    }

}


package com.example.jwt.service;

import com.example.jwt.domain.Member;
import com.example.jwt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        memberRepository.duplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<Member> findOneByEmail(String email) {
        return memberRepository.findOneByEmail(email);
    }

}


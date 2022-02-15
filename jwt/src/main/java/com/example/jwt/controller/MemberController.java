package com.example.jwt.controller;

import com.example.jwt.controller.dto.MemberDto;
import com.example.jwt.domain.Member;
import com.example.jwt.controller.dto.LoginDto;
import com.example.jwt.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/new")
    public String joinPage(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "join";
    }

    @PostMapping("/new")
    public String join(@Valid MemberDto memberDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "join";
        }

        Member member = createMemberFunction(memberDto);

        Long joinId = memberService.join(member);

        return "login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("memberDto", new LoginDto());

        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid MemberDto memberDto, BindingResult bindingResult, PasswordEncoder encoder, Model model) {


        if (bindingResult.hasErrors()) {
            return "login";
        }


        return "main";
    }


    public Member createMemberFunction(MemberDto memberDto) {
        return Member.createMember(memberDto.getEmail(), memberDto.getPassword());
    }


}

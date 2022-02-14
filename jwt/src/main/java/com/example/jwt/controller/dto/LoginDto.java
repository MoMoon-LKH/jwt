package com.example.jwt.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private Long id;
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String pw;
}

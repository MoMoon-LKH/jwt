package com.example.jwt.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    @NotEmpty(message = "이메일을 입력해주세요")
    @Size(min = 5, max = 50)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Size(min = 5, max = 100)
    private String password;

}

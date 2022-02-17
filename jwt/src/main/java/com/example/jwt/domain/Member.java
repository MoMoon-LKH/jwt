package com.example.jwt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter

@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 50, unique = true)
    private String email;

    @JsonIgnore
    @Column(length = 100)
    private String pw;

    private String role;

    private Member(String email, String pw, String role) {
        this.email = email;
        this.pw = pw;
        this.role = role;
    }

    public static Member createMember(String email, String pw, String role) {
        return new Member(email, pw, role);
    }


}

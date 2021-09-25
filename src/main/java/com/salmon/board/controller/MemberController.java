package com.salmon.board.controller;

import com.salmon.board.domain.Member;
import com.salmon.board.repository.MemberRepository;
import com.salmon.board.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    // 회원가입
    @PostMapping("/join")
    public Long join(@RequestBody Map<String, String> member) {
        Member newMember = new Member(
                member.get("email"),
                passwordEncoder.encode(member.get("password")),
                Collections.singletonList("ROLE_USER")
        );


        return memberRepository.save(newMember).getId();
    }

    // 로그인
    @PostMapping("/login")
    public loginResponseDto login(@ModelAttribute  @RequestBody loginRequestDto loginRequestDto) {
        System.out.println("login");

        Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            return new loginResponseDto("fail", null);
        }


        return new loginResponseDto("success", jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
    }


    @AllArgsConstructor
    @Getter
    private class loginResponseDto{
        private String msg;
        private String jwtToken;
    }

    @AllArgsConstructor
    @Getter
    private class loginRequestDto{
        private String email;
        private String password;
    }

}

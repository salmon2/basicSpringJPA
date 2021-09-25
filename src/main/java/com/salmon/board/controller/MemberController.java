package com.salmon.board.controller;

import com.salmon.board.domain.dto.member.MemberDto;
import com.salmon.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 로그인 페이지
    @GetMapping("/login")
    public String userLoginPage() {
        return "login";
    }

    // 홈페이지
    @GetMapping("/")
    public String home(HttpSession httpSession) throws Exception {
        if(memberService.loginRedirect(httpSession) == false)
            return "login";
        else
            return "boardList";

    }



    //로그인 처리
    @PostMapping("/login")
    public String userLogin(@ModelAttribute @RequestBody MemberDto dto, HttpSession session) throws Exception {
        System.out.println("login");
        boolean result = memberService.memberLogin(dto, session);

        //로그인 성공
        if (result == true) {
            return "redirect:/board/List";
        }
        //로그인 실패
        else {
            return "login";
        }
    }

    // 회원가입
    @PostMapping("/join")
    public String userJoin(@ModelAttribute MemberDto dto) throws Exception {
        System.out.println("join");
        memberService.userJoin(dto);

        return "login";
    }


    // 아이디 중복 검사
    @RequestMapping("/emailCheck")
    public void idCheck(@RequestParam String email, HttpServletResponse res) throws Exception {
        int result = 0;
        if (memberService.idCheck(email) != 0) {
            result = 1;
        }
        res.getWriter().print(result);
    }

    // 로그아웃
    @GetMapping("/logout")
    public String userLogout(HttpSession session) throws Exception {
        memberService.userLogout(session);
        return "redirect:/login";
    }




}

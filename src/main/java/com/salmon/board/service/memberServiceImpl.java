package com.salmon.board.service;

import com.salmon.board.domain.Member;
import com.salmon.board.domain.dto.member.MemberDto;
import com.salmon.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

@RequiredArgsConstructor
@Service
public class memberServiceImpl implements MemberService{

    @Autowired
    private static Hashtable<String, String> loginUsers = new Hashtable<String, String>();

    private final MemberRepository memberRepository;


    //로그인 확인 function
    public boolean loginCheck(HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");

        //로그아웃
        if(email == null){
            return false;
        }
        //로그인
        else
            return true;
    }
    //로그인 확인 내부 function2
    public boolean isLogin(String email) {
        boolean isLogin = false;
        Enumeration<String> e = loginUsers.keys();
        String key = "";
        while (e.hasMoreElements()) {
            key = (String) e.nextElement();
            if (email.equals(loginUsers.get(key)))
                isLogin = true;
        }
        return isLogin;
    }

    //세션 저장
    public void setSession(HttpSession session, MemberDto dto) {
        System.out.println("session.getId() = " + session.getId());
        loginUsers.put(session.getId(), dto.getEmail());
        session.setAttribute("email", dto.getEmail());
    }


    //로그인
    @Override
    public boolean memberLogin(MemberDto dto, HttpSession session) throws Exception {
        boolean isLogin = isLogin(dto.getEmail());
        //로그인 상태 아님
        if (isLogin == false) {
            Member findMember = memberRepository.findByEmail(dto.getEmail());
            // 회원정보 있음
            if (findMember.getPassword().equals(dto.getPassword())) {
                setSession(session, dto);
                return true;
            }
            else
                return false;
        }
        return false;
    }

    //회원가입
    @Override
    @Transactional
    public void userJoin(MemberDto dto) throws Exception {
        Member newMember = new Member(dto.getEmail(), dto.getPassword());

        memberRepository.save(newMember);
    }



    //로그아웃
    @Override
    public void userLogout(HttpSession session) throws Exception {
        loginUsers.remove(session.getId());
        session.invalidate();
    }


    //이메일 체크
    @Override
    public int idCheck(String email) throws Exception {
        Member findEmail = memberRepository.findByEmail(email);
        if(findEmail == null){
            return 0;
        }
        else
            return 1;
    }

}

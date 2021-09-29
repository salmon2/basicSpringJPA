package com.salmon.board.service;

import com.salmon.board.domain.dto.member.MemberDto;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface MemberService {
    public boolean memberLogin(MemberDto dto, HttpSession session) throws Exception;
    public void userJoin(MemberDto dto) throws Exception;
    public void userLogout(HttpSession session) throws Exception;
    public int idCheck(String email) throws Exception;
    public boolean loginCheck(HttpSession httpSession);

}

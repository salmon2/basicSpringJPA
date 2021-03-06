package com.salmon.board.controller;


import com.salmon.board.domain.Board;
import com.salmon.board.domain.dto.board.BoardListResponseDto;
import com.salmon.board.domain.dto.board.BoardRequestDto;
import com.salmon.board.domain.dto.board.BoardResponseDto;
import com.salmon.board.service.BoardService;
import com.salmon.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;

    //Test
    @GetMapping("/hello")
    public String hello(Model model, HttpSession httpSession, @RequestParam Long id){
        String email = (String)httpSession.getAttribute("email");
        System.out.println("email = " + email);

        List<BoardListResponseDto> findBoardList = boardService.findAll();

        model.addAttribute("cnt", findBoardList);
        model.addAttribute("test", id);

        return "hello";
    }

    //Create Page Rendering
    @GetMapping("/board/save")
    public String saveBoardRendering(HttpSession httpSession){
        if(memberService.loginCheck(httpSession) == false)
            return "login";
        return "addForm";
    }

    //Create Post
    @PostMapping("/board/save")
    public String saveBoard(HttpSession httpSession, @RequestBody @ModelAttribute BoardRequestDto boardRequestDto){
        if(memberService.loginCheck(httpSession) == false)
            return "login";

        String email = (String)httpSession.getAttribute("email");

        Board save = boardService.save(email, boardRequestDto);

        return "redirect:/board/List";
    }

    //Read One Rendering
    @GetMapping("/board")
    public String readBoard(HttpSession httpSession, @RequestParam(value = "id", defaultValue = "0", required = true) Long id, Model model){
        if(memberService.loginCheck(httpSession) == false)
            return "login";

        BoardResponseDto findBoard = boardService.findById(id);
        model.addAttribute("board", findBoard);

        return "board";
    }

    //Read List Rendering
    @GetMapping("/board/List")
    public String readBoardList(Model model, HttpSession httpSession){
        if(memberService.loginCheck(httpSession) == false)
            return "login";

        List<BoardListResponseDto> findBoardList = boardService.findAll();
        model.addAttribute("boardList", findBoardList);

        return "boardList";
    }

    //Update Page Rendering
    @GetMapping("/board/update")
    public String updateBoardRendering(@RequestParam(value = "id", required = true) Long id, HttpSession httpSession, Model  model){
        if(memberService.loginCheck(httpSession) == false)
            return "login";
        BoardResponseDto findBoard = boardService.findById(id);

        model.addAttribute("board", findBoard);

        return "editForm";
    }

    //Update
    @PutMapping("/board/update")
    public String updateBoard(HttpSession httpSession, @RequestParam(value = "id", required = true)Long id, @RequestBody @ModelAttribute BoardRequestDto boardRequestDto){
        if(memberService.loginCheck(httpSession) == false)
            return "login";

        Board updateBoard = boardService.update(id, boardRequestDto);

        return "boardList";
    }

    //Delete
    @GetMapping("/board/delete")
    public String deleteBoard(HttpSession httpSession, @RequestParam(value = "id", required = true)Long id){
        if(memberService.loginCheck(httpSession) == false)
            return "login";
        boardService.delete(id);
        return "redirect:/board/List";
    }

}

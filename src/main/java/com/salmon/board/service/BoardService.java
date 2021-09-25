package com.salmon.board.service;

import com.salmon.board.domain.Board;
import com.salmon.board.domain.dto.board.BoardListResponseDto;
import com.salmon.board.domain.dto.board.BoardRequestDto;
import com.salmon.board.domain.dto.board.BoardResponseDto;

import java.util.List;

public interface BoardService {
    public Board save(String email, BoardRequestDto boardRequestDto);
    public List<BoardListResponseDto> findAll();
    public BoardResponseDto findById(Long id);
    public Board update(Long id, BoardRequestDto boardRequestDto);
    public void delete(Long id);
}

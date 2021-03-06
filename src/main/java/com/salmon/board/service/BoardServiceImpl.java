package com.salmon.board.service;


import com.salmon.board.domain.Board;
import com.salmon.board.domain.dto.board.BoardListResponseDto;
import com.salmon.board.domain.dto.board.BoardRequestDto;
import com.salmon.board.domain.dto.board.BoardResponseDto;
import com.salmon.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.salmon.board.domain.Timestamped.TimeToString;


@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;


    //Create
    @Transactional
    @Override
    public Board save(String email, BoardRequestDto boardRequestDto){
        Board newBoard = new Board(boardRequestDto.getTitle(),
                boardRequestDto.getContent(), email);
        Board save = boardRepository.save(newBoard);
        return save;
    }

    //Read All
    @Override
    public List<BoardListResponseDto> findAll(){
        List<Board> findBoardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<BoardListResponseDto> boardListResponseDtoList = new ArrayList<>();

        for (Board board : findBoardList) {
            BoardListResponseDto newBoardListResponseDto =
                    new BoardListResponseDto(board.getId(), board.getTitle(),
                            board.getWriter(), TimeToString(board.getCreatedAt()));
            boardListResponseDtoList.add(newBoardListResponseDto);
        }

        return boardListResponseDtoList;
    }

    //Read One
    @Override
    public BoardResponseDto findById(Long id){
        Optional<Board> optional = Optional.ofNullable(boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        ));
        Board findBoard = optional.get();

        BoardResponseDto findBoardResponseDto = new BoardResponseDto(
                findBoard.getId(), findBoard.getTitle(), findBoard.getWriter(),
                findBoard.getContent(), TimeToString(findBoard.getCreatedAt()));

        return findBoardResponseDto;
    }

    //Update
    @Override
    @Transactional
    public Board update(Long id, BoardRequestDto boardRequestDto){
        Board findboard = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        findboard.update(boardRequestDto);
        return findboard;
    }

    //Delete
    @Transactional
    @Override
    public void delete(Long id){
        boardRepository.deleteById(id);

    }

}

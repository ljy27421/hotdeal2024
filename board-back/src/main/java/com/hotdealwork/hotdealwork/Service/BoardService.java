package com.hotdealwork.hotdealwork.Service;

import com.hotdealwork.hotdealwork.entity.Board;
import com.hotdealwork.hotdealwork.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void boardWrite(Board board) {

        boardRepository.save(board);
    }

    // 글 리스트 처리
    public List<Board> boardList() {
        return boardRepository.findAll();
    }

    // 글 불러오기 처리
    public Board boardView(Integer id) {

        return boardRepository.findById(id).get();
    }

    // 특정 글 삭제
    public void boardDelete(Integer id) {

        boardRepository.deleteById(id);
    }
}

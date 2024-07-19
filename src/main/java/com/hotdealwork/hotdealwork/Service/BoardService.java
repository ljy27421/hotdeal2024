package com.hotdealwork.hotdealwork.Service;

import com.hotdealwork.hotdealwork.entity.Board;
import com.hotdealwork.hotdealwork.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void boardWrite(Board board) {

        boardRepository.save(board);
    }

    // 글 리스트 처리
    public Page<Board> boardList(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    // 카테고리 별 글 리스트 처리

    public Page<Board> boardCategoryList(String category, Pageable pageable) {

        return boardRepository.findByCategory(category, pageable);
    }

    // 제목 검색 리스트 처리
    public Page<Board> boardTSearchList(String searchKeyword, Pageable pageable) {

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    // 카테고리 별 제목 검색 리스트 처리

    public Page<Board> boardCategoryTSearchList(String searchKeyword, String category, Pageable pageable) {

        return boardRepository.findByTitleContainingAndCategory(searchKeyword, category, pageable);
    }

    // 내용 검색 리스트 처리
    public Page<Board> boardCSearchList(String searchKeyword, Pageable pageable) {

        return boardRepository.findByContentContaining(searchKeyword, pageable);
    }

    // 카테고리 별 내용 검색 리스트 처리

    public Page<Board> boardCategoryCSearchList(String searchKeyword, String category, Pageable pageable) {

        return boardRepository.findByContentContainingAndCategory(searchKeyword, category, pageable);
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

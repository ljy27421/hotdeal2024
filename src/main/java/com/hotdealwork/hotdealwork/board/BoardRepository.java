package com.hotdealwork.hotdealwork.board;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query("SELECT b FROM Board b WHERE b.reported = true")  // 신고된 게시물 필터링
    List<Board> findReportedBoards();
}
//    Page<Board> findByCategory(String category, Pageable pageable);
//    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
//    Page<Board> findByTitleContainingAndCategory(String searchKeyword, String category, Pageable pageable);
//    Page<Board> findByContentContaining(String searchKeyword, Pageable pageable);
//    Page<Board> findByContentContainingAndCategory(String searchKeyword, String category, Pageable pageable);


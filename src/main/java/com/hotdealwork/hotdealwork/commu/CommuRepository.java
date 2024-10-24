package com.hotdealwork.hotdealwork.commu;


import com.hotdealwork.hotdealwork.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommuRepository extends JpaRepository<Commu, Integer> {

    @Query("SELECT b FROM Commu b WHERE b.reported = true")
    List<Commu> findReportedCommus();
//    Page<Board> findByCategory(String category, Pageable pageable);
//    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
//    Page<Board> findByTitleContainingAndCategory(String searchKeyword, String category, Pageable pageable);
//    Page<Board> findByContentContaining(String searchKeyword, Pageable pageable);
//    Page<Board> findByContentContainingAndCategory(String searchKeyword, String category, Pageable pageable);

}

package com.hotdealwork.hotdealwork.repository;


import com.hotdealwork.hotdealwork.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
    Page<Board> findByCategory(String category, Pageable pageable);
    Page<Board> findByTitleContainingAndCategory(String searchKeyword, String category, Pageable pageable);
}

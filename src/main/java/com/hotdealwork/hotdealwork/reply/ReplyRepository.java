package com.hotdealwork.hotdealwork.reply;

import com.hotdealwork.hotdealwork.board.Board;
import com.hotdealwork.hotdealwork.commu.Commu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByBoard(Board board);
    List<Reply> findByCommu(Commu commu);
}

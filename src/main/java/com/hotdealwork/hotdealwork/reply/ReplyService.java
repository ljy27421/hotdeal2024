package com.hotdealwork.hotdealwork.reply;

import com.hotdealwork.hotdealwork.board.Board;
import com.hotdealwork.hotdealwork.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    public Reply create(Board board, SiteUser author, String content) {
        Reply reply = new Reply();
        reply.setBoard(board);
        reply.setAuthor(author);
        reply.setContent(content);
        return replyRepository.save(reply);
    }

    public List<Reply> getReplyByBoard(Board board) {
        return replyRepository.findByBoard(board);
    }

    public Optional<Reply> getReplyById(Long id) {
        return replyRepository.findById(id);
    }

    public Reply updateReply(Reply reply, String content) {
        reply.setContent(content);
        return replyRepository.save(reply);
    }

    public void deleteReply(Reply reply) {
        replyRepository.delete(reply);
    }
}

package com.hotdealwork.hotdealwork.board;

import com.hotdealwork.hotdealwork.DataNotFoundException;
import com.hotdealwork.hotdealwork.user.SiteUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ImageRepository imageRepository;

    private final JPAQueryFactory queryFactory;

    @Autowired
    public BoardService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Board getBoard(Integer id) {
        Optional<Board> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        } else {
            throw new DataNotFoundException("board not found");
        }
    }

    // 글 작성 처리
    public void boardWrite(Board board, List<MultipartFile> files, SiteUser author) throws Exception{

        if (board.getImages() == null) {
            board.setImages(new ArrayList<>());
        }

        if(files != null && !files.isEmpty()){
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid + "_" + file.getOriginalFilename();
                    File saveFile = new File(projectPath, fileName);
                    file.transferTo(saveFile);

                    Image image = new Image();
                    image.setFilename(fileName);
                    image.setFilepath("/files/" + fileName);
                    image.setBoard(board);
                    board.getImages().add(image);
                }
            }
        }
        board.setAuthor(author);

        boardRepository.save(board);
    }

    // 동적 쿼리 리스트 처리
    public Page<Board> boardList(String searchKeyword, String category, String searchType, int hot, Pageable pageable) {
        QBoard board = QBoard.board;
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(searchKeyword)) {
            if ("title".equals(searchType)) {
                builder.and(board.title.containsIgnoreCase(searchKeyword));
            } else if ("content".equals(searchType)) {
                builder.and(board.content.containsIgnoreCase(searchKeyword));
            } else if ("torc".equals(searchType)){
                builder.and(board.title.containsIgnoreCase(searchKeyword)
                        .or(board.content.containsIgnoreCase(searchKeyword)));
            } else {
                builder.and(board.author.username.containsIgnoreCase(searchKeyword));
            }
        }

        if (StringUtils.hasText(category)) {
            builder.and(board.category.eq(category));
        }

        if (hot > 0) {
            builder.and(board.liked.size().goe(hot));
        }

        List<Board> result = queryFactory
                .selectFrom(board)
                .where(builder)
                .offset(pageable.getOffset())
                .orderBy(board.id.desc())
                .limit(pageable.getPageSize())
                .fetch();

        long total =queryFactory
                .selectFrom(board)
                .where(builder)
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    // 글 불러오기 처리
    public Board boardView(Integer id) {

        return boardRepository.findById(id).get();
    }

    // 특정 글 삭제
    public void boardDelete(Integer id) {

        Board board = boardRepository.findById(id).orElseThrow();
        for (Image image : board.getImages()) {
            File file = new File(System.getProperty("user.dir") + "/src/main/resources/static/files/" + image.getFilename());
            if (file.exists()) {
                file.delete();
            }
            imageRepository.delete(image);
        }

        boardRepository.delete(board);
    }

    // 첨부된 이미지 삭제
    public void deleteImages(List<Long> deleteImageIds) {
        for (Long imageId : deleteImageIds) {
            Image image = imageRepository.findById(imageId).orElseThrow();
            File file = new File(System.getProperty("user.dir") + "/src/main/resources/static/files/" + image.getFilename());
            if (file.exists()) {
                file.delete();
            }
            imageRepository.delete(image);
        }
    }

    // 게시글 추천
    public void boardLike(Board board, SiteUser siteUser) {
        board.getLiked().add(siteUser);
        boardRepository.save(board);
    }

}

package com.hotdealwork.hotdealwork.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ImageRepository imageRepository;

    // 글 작성 처리
    public void boardWrite(Board board, List<MultipartFile> files) throws Exception{

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

}

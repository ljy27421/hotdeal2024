package com.hotdealwork.hotdealwork.commu;

import com.hotdealwork.hotdealwork.DataNotFoundException;
import com.hotdealwork.hotdealwork.image.Image;
import com.hotdealwork.hotdealwork.image.ImageRepository;
import com.hotdealwork.hotdealwork.user.SiteUser;
import com.hotdealwork.hotdealwork.user.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommuService {

    @Autowired
    private CommuRepository commuRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    private final JPAQueryFactory queryFactory;

    @Autowired
    public CommuService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    //    public Commu getCommu(Integer id) {
//        Optional<Commu> commu = this.commuRepository.findById(id);
//        if (commu.isPresent()) {
//            Commu commu1 = commu.get();
//            commu1.setView(commu1.getView()+1);
//            this.commuRepository.save(commu1);
//            return commu1;
//        } else {
//            throw new DataNotFoundException("commu not found");
//        }
//    }
    public Commu getCommu(Integer id) {
        return commuRepository.findById(id).orElseThrow(() -> new DataNotFoundException("commu not found"));
    }

    // 조회수 증가
    public void commuIncreaseViewCount(Commu commu) {
        commu.setView(commu.getView() + 1);
        commuRepository.save(commu);
    }

    // 글 작성 처리
    public void commuWrite(Commu commu, List<MultipartFile> files, SiteUser author) throws Exception{

        Commu existingCommu = commu.getId() != null ? commuRepository.findById(commu.getId()).orElse(null) : null;
        if (existingCommu != null) {
            commu.setReplies(existingCommu.getReplies());
        }

        if (commu.getImages() == null) {
            commu.setImages(new ArrayList<>());
        }

        if(files != null && !files.isEmpty()){
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/files";
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid + "_" + file.getOriginalFilename();
                    File saveFile = new File(projectPath, fileName);
                    file.transferTo(saveFile);

                    Image image = new Image();
                    image.setFilename(fileName);
                    image.setFilepath("/files/" + fileName);
                    image.setCommu(commu);
                    commu.getImages().add(image);
                }
            }
        }

        commu.setAuthor(author);

        commuRepository.save(commu);
    }

    // 동적 쿼리 리스트 처리
    public Page<CommuDTO> commuList(String searchKeyword, String category, String searchType, int hot, Pageable pageable) {
        QCommu commu = QCommu.commu;
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(searchKeyword)) {
            if ("title".equals(searchType)) {
                builder.and(commu.title.containsIgnoreCase(searchKeyword));
            } else if ("content".equals(searchType)) {
                builder.and(commu.content.containsIgnoreCase(searchKeyword));
            } else if ("torc".equals(searchType)){
                builder.and(commu.title.containsIgnoreCase(searchKeyword)
                        .or(commu.content.containsIgnoreCase(searchKeyword)));
            } else {
                builder.and(commu.author.username.containsIgnoreCase(searchKeyword));
            }
        }

        if (StringUtils.hasText(category)) {
            builder.and(commu.category.eq(category));
        }

        if (hot > 0) {
            builder.and(commu.liked.goe(hot));
        }

        if (!"공지사항".equals(category)) {
            builder.and(commu.category.ne("공지사항"));
        }

        List<CommuDTO> result = queryFactory
                .select(Projections.constructor(CommuDTO.class,
                        commu.id,
                        commu.title,
                        commu.category,
                        commu.createdDate,
                        commu.liked,
                        commu.author,
                        commu.view

                        ))
                .from(commu)
                .where(builder)
                .offset(pageable.getOffset())
                .orderBy(commu.id.desc())
                .limit(pageable.getPageSize())
                .fetch();

        long total =queryFactory
                .selectFrom(commu)
                .where(builder)
                .fetch().size();
        ;

        return new PageImpl<>(result, pageable, total);
    }

    public Page<CommuDTO> commuNotice(Pageable pageable) {
        QCommu commu = QCommu.commu;
        BooleanBuilder builder = new BooleanBuilder();

        // 공지사항만 필터링
        builder.and(commu.category.eq("공지사항"));

        List<CommuDTO> result = queryFactory
                .select(Projections.constructor(CommuDTO.class,
                        commu.id,
                        commu.title,
                        commu.category,
                        commu.createdDate,
                        commu.liked,
                        commu.author,
                        commu.view
                ))
                .from(commu)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(commu)
                .where(builder)
                .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }

    // 메인 페이지 인기글 처리
    public List<CommuMainDTO> commuMainHot() {
        QCommu commu = QCommu.commu;

        return queryFactory
                .select(Projections.constructor(CommuMainDTO.class,
                        commu.id,
                        commu.title
                ))
                .from(commu)
                .where(commu.createdDate.after(LocalDateTime.now().minusWeeks(10)))
                .orderBy(commu.liked.desc())
                .limit(5)
                .fetch();
    }


    // 글 불러오기 처리
//    public Commu commuView(Integer id) {
//
//        return commuRepository.findById(id).get();
//    }

    // 특정 글 삭제
    public void commuDelete(Integer id) {

        Commu commu = commuRepository.findById(id).orElseThrow();
        for (Image image : commu.getImages()) {
            File file = new File(System.getProperty("user.dir") + "/src/main/resources/static/files/" + image.getFilename());
            if (file.exists()) {
                file.delete();
            }
            imageRepository.delete(image);
        }

        commuRepository.delete(commu);
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
    public void commuLike(Commu commu, SiteUser siteUser) {
        commu.setLiked(commu.getLiked() + 1);
        siteUser.getCommuLikes().add(commu.getId());
        commuRepository.save(commu);
        userRepository.save(siteUser);
    }
    // 게시글 비추천
    public void commuDislike(Commu commu, SiteUser siteUser) {
        commu.setLiked(commu.getLiked() - 1);
        siteUser.getCommuDislikes().add(commu.getId());
        commuRepository.save(commu);
        userRepository.save(siteUser);
    }

    // 신고된 게시글 목록 조회
    public List<Commu> getReportedPosts() {
        QCommu commu = QCommu.commu;
        return queryFactory.selectFrom(commu)
                .where(commu.category.eq("reported"))
                .fetch();
    }
    // 게시글 신고 처리
    public void reportPost(Integer id) {
        Commu commu = getCommu(id);
        commu.setReported(true);
        commuRepository.save(commu);
    }

    // 게시글 신고 해제
    public void unreportPost(Integer id) {
        Commu commu = getCommu(id);
        commu.setReported(false);
        commuRepository.save(commu);
    }
    // 신고된 게시글 삭제
    public void deleteReportedPost(Integer id) {
        commuDelete(id);
    }
}

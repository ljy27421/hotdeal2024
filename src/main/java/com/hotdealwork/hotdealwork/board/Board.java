package com.hotdealwork.hotdealwork.board;

import com.hotdealwork.hotdealwork.image.Image;
import com.hotdealwork.hotdealwork.user.SiteUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String category;

    private String mall;
    private String productName;
    private Long price;
    private String saleUrl;
    private LocalDate startDate;
    private LocalDate endDate;

    private Boolean expired = false;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer view = 0;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @ManyToOne
    private SiteUser author;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer liked = 0;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    // 신고된 게시물 여부를 나타내는 필드 추가 (기본값: false)
    private Boolean reported = false;

    // 신고된 게시물 여부 설정 메서드
    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public boolean isReported() {
        return reported;
    }

    // 게시글의 임베딩 벡터 (예시: NLP 또는 AI 모델에 사용)
    private List<Double> embeddingVector;

//    @PrePersist
//    protected void onCreate() {
//        this.createdDate = LocalDateTime.now();
//    }
//
//    @Override
//    public String toString() {
//        return "Board{id=" + id + ", title='" + title + "', content='" + content + "', category='" + category + "', images=" + images + '}';
//    }

}
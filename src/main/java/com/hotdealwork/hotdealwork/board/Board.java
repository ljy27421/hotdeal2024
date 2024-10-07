package com.hotdealwork.hotdealwork.board;

import com.hotdealwork.hotdealwork.image.Image;
import com.hotdealwork.hotdealwork.user.SiteUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    @ManyToMany
    private Set<SiteUser> liked;

    @ManyToMany
    private Set<SiteUser> disliked;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    // 신고된 게시물 여부를 나타내는 필드 추가 (기본값: false)
    private Boolean reported = false;

    // 임베딩 벡터 추가 (사용자 선호도 분석 관련)
    @ElementCollection
    private List<Double> embeddingVector;

    // Getter 및 Setter 메서드 추가
    public List<Double> getEmbeddingVector() {
        return embeddingVector;
    }

    public void setEmbeddingVector(List<Double> embeddingVector) {
        this.embeddingVector = embeddingVector;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    // 좋아요와 싫어요 필드 접근자
    public Set<SiteUser> getLiked() {
        return liked;
    }

    public Set<SiteUser> getDisliked() {
        return disliked;
    }

    public String getProductName() {
        return productName;
    }

    public String getMall() {
        return mall;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }
}
package com.hotdealwork.hotdealwork.notice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;  // 공지사항 제목
    private String content;  // 공지사항 내용
    private LocalDateTime createdAt = LocalDateTime.now();  // 생성일시
}
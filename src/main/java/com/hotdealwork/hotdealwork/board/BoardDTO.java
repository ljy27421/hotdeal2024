package com.hotdealwork.hotdealwork.board;

import com.hotdealwork.hotdealwork.user.SiteUser;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class BoardDTO {
    private Integer id;
    private String title;
    private String category;
    private LocalDate endDate;
    private Boolean expired;
    private Integer view;
    private SiteUser author;
    private Integer liked;
    private LocalDateTime createdDate;

    @Builder
    public BoardDTO(Integer id, String title, String category, LocalDateTime createdDate,
                    LocalDate endDate, Integer liked, Boolean expired, SiteUser author, Integer view) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.createdDate = createdDate;
        this.endDate = endDate;
        this.liked = liked;
        this.expired = expired;
        this.author = author;
        this.view = view;
    }

}

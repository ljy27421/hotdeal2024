package com.hotdealwork.hotdealwork.commu;

import com.hotdealwork.hotdealwork.user.SiteUser;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CommuDTO {
    private Integer id;
    private String title;
    private String category;
    private Integer view;
    private SiteUser author;
    private Integer liked;
    private LocalDateTime createdDate;

    @Builder
    public CommuDTO(Integer id, String title, String category, LocalDateTime createdDate,
                    Integer liked, SiteUser author, Integer view) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.createdDate = createdDate;
        this.liked = liked;
        this.author = author;
        this.view = view;
    }
}

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
    private Integer view;
    private SiteUser author;
    private Integer liked;
    private LocalDateTime createdDate;
    private Long price;
    private Long deliveryPrice;

    @Builder
    public BoardDTO(Integer id, String title, String category, LocalDateTime createdDate,
                    LocalDate endDate, Integer liked, SiteUser author, Integer view, Long price, Long deliveryPrice) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.createdDate = createdDate;
        this.endDate = endDate;
        this.liked = liked;
        this.author = author;
        this.view = view;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
    }

}

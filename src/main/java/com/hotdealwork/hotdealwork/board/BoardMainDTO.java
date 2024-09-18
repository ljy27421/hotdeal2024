package com.hotdealwork.hotdealwork.board;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardMainDTO {
    private Integer id;
    private String title;

    @Builder
    public BoardMainDTO(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

}

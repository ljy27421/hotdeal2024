package com.hotdealwork.hotdealwork.commu;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommuMainDTO {
    private Integer id;
    private String title;

    @Builder
    public CommuMainDTO(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

}

package com.hotdealwork.hotdealwork.image;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hotdealwork.hotdealwork.board.Board;
import com.hotdealwork.hotdealwork.commu.Commu;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private String filepath;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @JsonBackReference
    private Board board;

    @ManyToOne
    @JoinColumn(name = "commu_id")
    @JsonBackReference
    private Commu commu;

    @Override
    public String toString() {
        return "Image{id=" + id + ", filename='" + filename + "', filepath='" + filepath + "'}";
    }


}

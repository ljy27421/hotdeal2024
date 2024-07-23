package com.hotdealwork.hotdealwork.board;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Override
    public String toString() {
        return "Image{id=" + id + ", filename='" + filename + "', filepath='" + filepath + "'}";
    }


}

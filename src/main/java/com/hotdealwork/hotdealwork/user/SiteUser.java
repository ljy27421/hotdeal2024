package com.hotdealwork.hotdealwork.user;

import com.hotdealwork.hotdealwork.board.Board;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data

public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String password;

    @ManyToMany
    Set<Board> likes;
}

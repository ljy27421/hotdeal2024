package com.hotdealwork.hotdealwork.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String password;
}

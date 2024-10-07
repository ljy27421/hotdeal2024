package com.hotdealwork.hotdealwork.commu;

import com.hotdealwork.hotdealwork.image.Image;
import com.hotdealwork.hotdealwork.user.SiteUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Commu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private  String content;
    private String category;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer view = 0;

    @OneToMany(mappedBy = "commu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    Set<SiteUser> liked;

    @ManyToMany
    Set<SiteUser> disliked;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Board{id=" + id + ", title='" + title + "', content='" + content + "', category='" + category + "', images=" + images + '}';
    }

}
package com.hotdealwork.hotdealwork.commu;

import com.hotdealwork.hotdealwork.image.Image;
import com.hotdealwork.hotdealwork.reply.Reply;
import com.hotdealwork.hotdealwork.user.SiteUser;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private String content;
    private String category;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer view = 0;

    @OneToMany(mappedBy = "commu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @OneToMany(mappedBy = "commu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SiteUser author;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer liked = 0;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean reported = false; // 신고 여부 추가

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
//
//    @Override
//    public String toString() {
//        return "Board{id=" + id + ", title='" + title + "', content='" + content + "', category='" + category + "', images=" + images + '}';
//    }

}
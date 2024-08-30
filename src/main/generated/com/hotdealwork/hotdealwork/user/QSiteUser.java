package com.hotdealwork.hotdealwork.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSiteUser is a Querydsl query type for SiteUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSiteUser extends EntityPathBase<SiteUser> {

    private static final long serialVersionUID = 1316536736L;

    public static final QSiteUser siteUser = new QSiteUser("siteUser");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Integer, NumberPath<Integer>> interest = this.<Integer, NumberPath<Integer>>createList("interest", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final SetPath<com.hotdealwork.hotdealwork.board.Board, com.hotdealwork.hotdealwork.board.QBoard> likes = this.<com.hotdealwork.hotdealwork.board.Board, com.hotdealwork.hotdealwork.board.QBoard>createSet("likes", com.hotdealwork.hotdealwork.board.Board.class, com.hotdealwork.hotdealwork.board.QBoard.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath username = createString("username");

    public QSiteUser(String variable) {
        super(SiteUser.class, forVariable(variable));
    }

    public QSiteUser(Path<? extends SiteUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSiteUser(PathMetadata metadata) {
        super(SiteUser.class, metadata);
    }

}


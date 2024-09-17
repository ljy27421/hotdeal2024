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

    public final ListPath<Integer, NumberPath<Integer>> commuDislikes = this.<Integer, NumberPath<Integer>>createList("commuDislikes", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final ListPath<Integer, NumberPath<Integer>> commuLikes = this.<Integer, NumberPath<Integer>>createList("commuLikes", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final ListPath<Integer, NumberPath<Integer>> dislikes = this.<Integer, NumberPath<Integer>>createList("dislikes", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Integer, NumberPath<Integer>> interest = this.<Integer, NumberPath<Integer>>createList("interest", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final ListPath<Integer, NumberPath<Integer>> likes = this.<Integer, NumberPath<Integer>>createList("likes", Integer.class, NumberPath.class, PathInits.DIRECT2);

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


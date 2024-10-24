package com.hotdealwork.hotdealwork.commu;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommu is a Querydsl query type for Commu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommu extends EntityPathBase<Commu> {

    private static final long serialVersionUID = -350743217L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommu commu = new QCommu("commu");

    public final com.hotdealwork.hotdealwork.user.QSiteUser author;

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final ListPath<com.hotdealwork.hotdealwork.image.Image, com.hotdealwork.hotdealwork.image.QImage> images = this.<com.hotdealwork.hotdealwork.image.Image, com.hotdealwork.hotdealwork.image.QImage>createList("images", com.hotdealwork.hotdealwork.image.Image.class, com.hotdealwork.hotdealwork.image.QImage.class, PathInits.DIRECT2);

    public final NumberPath<Integer> liked = createNumber("liked", Integer.class);

    public final ListPath<com.hotdealwork.hotdealwork.reply.Reply, com.hotdealwork.hotdealwork.reply.QReply> replies = this.<com.hotdealwork.hotdealwork.reply.Reply, com.hotdealwork.hotdealwork.reply.QReply>createList("replies", com.hotdealwork.hotdealwork.reply.Reply.class, com.hotdealwork.hotdealwork.reply.QReply.class, PathInits.DIRECT2);

    public final BooleanPath reported = createBoolean("reported");

    public final StringPath title = createString("title");

    public final NumberPath<Integer> view = createNumber("view", Integer.class);

    public QCommu(String variable) {
        this(Commu.class, forVariable(variable), INITS);
    }

    public QCommu(Path<? extends Commu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommu(PathMetadata metadata, PathInits inits) {
        this(Commu.class, metadata, inits);
    }

    public QCommu(Class<? extends Commu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.hotdealwork.hotdealwork.user.QSiteUser(forProperty("author")) : null;
    }

}


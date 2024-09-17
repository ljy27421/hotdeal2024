package com.hotdealwork.hotdealwork.reply;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReply is a Querydsl query type for Reply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReply extends EntityPathBase<Reply> {

    private static final long serialVersionUID = 1123291281L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReply reply = new QReply("reply");

    public final com.hotdealwork.hotdealwork.user.QSiteUser author;

    public final com.hotdealwork.hotdealwork.board.QBoard board;

    public final com.hotdealwork.hotdealwork.commu.QCommu commu;

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QReply(String variable) {
        this(Reply.class, forVariable(variable), INITS);
    }

    public QReply(Path<? extends Reply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReply(PathMetadata metadata, PathInits inits) {
        this(Reply.class, metadata, inits);
    }

    public QReply(Class<? extends Reply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.hotdealwork.hotdealwork.user.QSiteUser(forProperty("author")) : null;
        this.board = inits.isInitialized("board") ? new com.hotdealwork.hotdealwork.board.QBoard(forProperty("board"), inits.get("board")) : null;
        this.commu = inits.isInitialized("commu") ? new com.hotdealwork.hotdealwork.commu.QCommu(forProperty("commu"), inits.get("commu")) : null;
    }

}


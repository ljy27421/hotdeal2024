package com.hotdealwork.hotdealwork.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -418653303L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final com.hotdealwork.hotdealwork.user.QSiteUser author;

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final ListPath<Double, NumberPath<Double>> embeddingVector = this.<Double, NumberPath<Double>>createList("embeddingVector", Double.class, NumberPath.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final BooleanPath expired = createBoolean("expired");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final ListPath<com.hotdealwork.hotdealwork.image.Image, com.hotdealwork.hotdealwork.image.QImage> images = this.<com.hotdealwork.hotdealwork.image.Image, com.hotdealwork.hotdealwork.image.QImage>createList("images", com.hotdealwork.hotdealwork.image.Image.class, com.hotdealwork.hotdealwork.image.QImage.class, PathInits.DIRECT2);

    public final NumberPath<Integer> liked = createNumber("liked", Integer.class);

    public final StringPath mall = createString("mall");

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final StringPath productName = createString("productName");

    public final BooleanPath reported = createBoolean("reported");

    public final StringPath saleUrl = createString("saleUrl");

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> view = createNumber("view", Integer.class);

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.hotdealwork.hotdealwork.user.QSiteUser(forProperty("author")) : null;
    }

}


package com.my.template.common.security.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserToken is a Querydsl query type for UserToken
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserToken extends EntityPathBase<UserToken> {

    private static final long serialVersionUID = 52353870L;

    public static final QUserToken userToken = new QUserToken("userToken");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath password = createString("password");

    public final StringPath token = createString("token");

    public final StringPath username = createString("username");

    public QUserToken(String variable) {
        super(UserToken.class, forVariable(variable));
    }

    public QUserToken(Path<? extends UserToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserToken(PathMetadata metadata) {
        super(UserToken.class, metadata);
    }

}


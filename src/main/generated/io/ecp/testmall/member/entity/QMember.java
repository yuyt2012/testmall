package io.ecp.testmall.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1846757899L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final QAddress address;

    public final ListPath<io.ecp.testmall.board.entity.Comment, io.ecp.testmall.board.entity.QComment> comments = this.<io.ecp.testmall.board.entity.Comment, io.ecp.testmall.board.entity.QComment>createList("comments", io.ecp.testmall.board.entity.Comment.class, io.ecp.testmall.board.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<io.ecp.testmall.order.entity.Order, io.ecp.testmall.order.entity.QOrder> orders = this.<io.ecp.testmall.order.entity.Order, io.ecp.testmall.order.entity.QOrder>createList("orders", io.ecp.testmall.order.entity.Order.class, io.ecp.testmall.order.entity.QOrder.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final ListPath<io.ecp.testmall.board.entity.Post, io.ecp.testmall.board.entity.QPost> posts = this.<io.ecp.testmall.board.entity.Post, io.ecp.testmall.board.entity.QPost>createList("posts", io.ecp.testmall.board.entity.Post.class, io.ecp.testmall.board.entity.QPost.class, PathInits.DIRECT2);

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath socialLogin = createString("socialLogin");

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
    }

}


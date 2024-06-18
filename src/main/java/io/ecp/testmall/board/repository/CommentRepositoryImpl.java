package io.ecp.testmall.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.ecp.testmall.board.entity.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

import static io.ecp.testmall.board.entity.QComment.*;

public class CommentRepositoryImpl implements CommentRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> findCommentByPostId(Long postId) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        return query.selectFrom(comment)
                .leftJoin(comment.parent)
                .fetchJoin()
                .where(comment.parent.id.eq(postId))
                .orderBy(comment.parent.id.asc().nullsFirst())
                .fetch();
    }
}

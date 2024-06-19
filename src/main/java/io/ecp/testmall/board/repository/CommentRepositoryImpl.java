package io.ecp.testmall.board.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.ecp.testmall.board.entity.Comment;
import io.ecp.testmall.board.entity.CommentListDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static io.ecp.testmall.board.entity.QComment.*;

public class CommentRepositoryImpl implements CommentRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<CommentListDTO> commentList(Long postId, Pageable pageable) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        List<CommentListDTO> commentListDTOS = query.select(comment)
                .from(comment)
                .where(comment.post.id.eq(postId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(CommentListDTO::new)
                .toList();

        JPAQuery<Long> count = query.select(comment.count())
                .from(comment)
                .where(comment.post.id.eq(postId));

        return PageableExecutionUtils.getPage(commentListDTOS, pageable, count::fetchOne);
    }
}

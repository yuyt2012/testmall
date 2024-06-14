package io.ecp.testmall.board.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.ecp.testmall.board.entity.PostListDTO;
import io.ecp.testmall.board.entity.QPost;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Comparator;
import java.util.List;

import static io.ecp.testmall.board.entity.QPost.*;

public class PostRepositoryImpl implements PostRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<PostListDTO> searchPost(Pageable pageable) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        List<PostListDTO> postListDTOS = query.select(post)
                .from(post)
                .orderBy(post.regDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(PostListDTO::new)
                .toList();

        JPAQuery<Long> count = query.select(post.count())
                .from(post);

        return PageableExecutionUtils.getPage(postListDTOS, pageable, count::fetchOne);
    }
}

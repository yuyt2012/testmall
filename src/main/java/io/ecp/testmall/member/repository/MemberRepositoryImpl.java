package io.ecp.testmall.member.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.ecp.testmall.member.entity.Member;
import io.ecp.testmall.member.entity.QMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static io.ecp.testmall.member.entity.QMember.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Member> searchPage(Pageable pageable) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        List<Member> members = query.select(member)
                .from(member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = query.select(member.count())
                .from(member);

        return PageableExecutionUtils.getPage(members, pageable, count::fetchOne);
    }
}

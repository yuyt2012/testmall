package io.ecp.testmall.member.repository;

import io.ecp.testmall.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

    Page<Member> searchPage(Pageable pageable);
}

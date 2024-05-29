package io.ecp.testmall.member.repository;

import io.ecp.testmall.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryImplTest {

    @Autowired
    private MemberRepositoryImpl memberRepository;

    @Test
    void searchPage() {
        // Given
        // Assume that there are some Member entities in the database

        // When
        Pageable pageable = PageRequest.of(0, 10); // Get the first 10 members
        Page<Member> page = memberRepository.searchPage(pageable);

        // Then
        assertNotNull(page);
        assertEquals(10, page.getSize());
        assertFalse(page.getContent().isEmpty());
    }
}
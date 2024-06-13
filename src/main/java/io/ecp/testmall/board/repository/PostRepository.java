package io.ecp.testmall.board.repository;

import io.ecp.testmall.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

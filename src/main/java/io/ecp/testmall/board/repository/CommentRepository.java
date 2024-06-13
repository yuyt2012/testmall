package io.ecp.testmall.board.repository;

import io.ecp.testmall.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

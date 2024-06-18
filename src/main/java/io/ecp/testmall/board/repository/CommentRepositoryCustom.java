package io.ecp.testmall.board.repository;

import io.ecp.testmall.board.entity.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findCommentByPostId(Long postId);
}

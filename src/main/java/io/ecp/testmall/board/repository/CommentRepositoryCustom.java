package io.ecp.testmall.board.repository;

import io.ecp.testmall.board.entity.Comment;
import io.ecp.testmall.board.entity.CommentListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositoryCustom {
    Page<CommentListDTO> commentList(Long postId, Pageable pageable);
}

package io.ecp.testmall.board.repository;

import io.ecp.testmall.board.entity.PostListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<PostListDTO> searchPost(Pageable pageable);
}

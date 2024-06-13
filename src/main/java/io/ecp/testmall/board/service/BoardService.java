package io.ecp.testmall.board.service;

import io.ecp.testmall.board.repository.CommentRepository;
import io.ecp.testmall.board.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
}

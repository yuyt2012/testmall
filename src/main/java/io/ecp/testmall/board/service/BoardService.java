package io.ecp.testmall.board.service;

import io.ecp.testmall.board.entity.*;
import io.ecp.testmall.board.repository.CommentRepository;
import io.ecp.testmall.board.repository.PostRepository;
import io.ecp.testmall.member.entity.Member;
import io.ecp.testmall.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BoardService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public BoardService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = false)
    public void registerPost(PostDTO postDTO) {
        
        Member member = memberRepository.findByEmail(postDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        Post post = new Post();
        post.setMember(member);
        post.setWriter(postDTO.getWriter());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setPassword(passwordEncoder.encode(postDTO.getPassword()));
        postRepository.save(post);
    }

    @Transactional(readOnly = false)
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional(readOnly = false)
    public void updatePost(UpdatePostDTO updatePostDTO, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
        if (!passwordEncoder.matches(updatePostDTO.getPassword(), post.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        post.setTitle(updatePostDTO.getTitle());
        post.setContent(updatePostDTO.getContent());
    }

    public Page<PostListDTO> getPostList(Pageable pageable) {
        return postRepository.searchPost(pageable);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
    }

    @Transactional(readOnly = false)
    public void registerComment(CommentDTO commentDTO) {

        Member member = memberRepository.findByEmail(commentDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
        Comment comment = new Comment();
        comment.setMember(member);
        comment.setPost(post);
        comment.setContent(commentDTO.getContent());
        commentRepository.save(comment);
    }
}

package io.ecp.testmall.board.controller;

import io.ecp.testmall.board.entity.CommentDTO;
import io.ecp.testmall.board.entity.PostDTO;
import io.ecp.testmall.board.entity.UpdatePostDTO;
import io.ecp.testmall.board.service.BoardService;
import org.aspectj.lang.annotation.control.CodeGenerationHint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static io.ecp.testmall.utils.tokenValidUtils.tokenValid;

@Controller
@CrossOrigin(origins = "http://localhost:5174")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registerpost")
    public ResponseEntity<?> registerPost(@RequestBody PostDTO postDTO,
                                          @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();
        boardService.registerPost(postDTO);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/postlist")
    public ResponseEntity<?> getPostList(Pageable pageable,
                                         @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "regDate"));
        return ResponseEntity.ok(boardService.getPostList(sortedPageable));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId,
                                     @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(boardService.getPost(postId));
    }

    @DeleteMapping("/deletepost/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId,
                                        @RequestBody String password,
                                        @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();
        if (passwordEncoder.matches(password, boardService.getPost(postId).getPassword())) {
            boardService.deletePost(postId);
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updatepost/{postId}")
    public ResponseEntity<?> updatePost(@RequestBody UpdatePostDTO updatePostDTO,
                                        @PathVariable Long postId,
                                        @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();
        if (passwordEncoder.matches(updatePostDTO.getPassword(), boardService.getPost(postId).getPassword())) {
            boardService.updatePost(updatePostDTO, postId);
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/registercomment")
    public ResponseEntity<?> registerComment(@RequestBody CommentDTO commentDTO,
                                             @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();
        boardService.registerComment(commentDTO);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/commentlist/{postId}")
    public ResponseEntity<?> getCommentList(@PathVariable Long postId,
                                            Pageable pageable,
                                            @RequestHeader("Authorization") String token) {
        if (tokenValid(token)) return ResponseEntity.badRequest().build();
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "regDate"));
        return ResponseEntity.ok(boardService.getCommentList(postId, sortedPageable));
    }
}
package io.ecp.testmall.board.entity;

import lombok.Data;

@Data
public class CommentListDTO {

    private Long postId;
    private String writer;
    private String content;
    private String regDate;

    public CommentListDTO(Comment comment) {
        this.postId = comment.getPost().getId();
        this.writer = comment.getMember().getName();
        this.content = comment.getContent();
        this.regDate = comment.getRegDate().toString();
    }
}

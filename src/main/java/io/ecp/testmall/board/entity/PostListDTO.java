package io.ecp.testmall.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PostListDTO {
    private Long id;
    private String title;
    private String writer;
    private String regDate;

    public PostListDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.writer = post.getWriter();
        this.regDate = post.getRegDate().toString();
    }
}

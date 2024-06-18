package io.ecp.testmall.board.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    @NotEmpty
    private String email;
    @NotEmpty
    private Long postId;
    @NotEmpty
    private String writer;
    @NotEmpty
    private String content;
    private Long parentId;
    private int depth;
    private boolean isDeleted;
}

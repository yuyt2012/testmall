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
public class PostDTO {

    @NotEmpty
    private String email;
    @NotEmpty
    private String writer;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String password;
}

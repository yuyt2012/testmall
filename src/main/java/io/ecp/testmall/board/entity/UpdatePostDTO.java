package io.ecp.testmall.board.entity;

import lombok.Data;

@Data
public class UpdatePostDTO {

    private String title;
    private String content;
    private String password;
}

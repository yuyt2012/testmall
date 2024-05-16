package io.ecp.testmall.member.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm {

    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}

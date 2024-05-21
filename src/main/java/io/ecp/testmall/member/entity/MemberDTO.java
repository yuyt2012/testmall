package io.ecp.testmall.member.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberDTO {

    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    private String socialId;
    private String phone;
    private String city;
    private String street;
    private String zipcode;
}

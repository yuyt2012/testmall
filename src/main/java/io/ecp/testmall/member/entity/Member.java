package io.ecp.testmall.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Embedded
    private Address address;
    private String socialLogin;

    public Member update(UpdateMemberDTO updateMemberDTO) {
        this.email = updateMemberDTO.getEmail();
        this.password = updateMemberDTO.getPassword();
        this.name = updateMemberDTO.getName();
        this.phone = updateMemberDTO.getPhone();
        this.address = Address.builder()
                .city(updateMemberDTO.getCity())
                .street(updateMemberDTO.getStreet())
                .zipcode(updateMemberDTO.getZipcode())
                .build();
        this.socialLogin = updateMemberDTO.getSocialLogin();
        return this;
    }
}

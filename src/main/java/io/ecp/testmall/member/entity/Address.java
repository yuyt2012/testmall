package io.ecp.testmall.member.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {

    private String city;
    private String street;
    private String zipcode;
}

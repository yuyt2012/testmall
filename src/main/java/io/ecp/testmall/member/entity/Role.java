package io.ecp.testmall.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String name;
}

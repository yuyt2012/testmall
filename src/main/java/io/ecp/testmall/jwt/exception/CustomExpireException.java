package io.ecp.testmall.jwt.exception;

import io.jsonwebtoken.ExpiredJwtException;

public class CustomExpireException extends ExpiredJwtException {

    private final String message;

    public CustomExpireException(String message, ExpiredJwtException source) {
        super(source.getHeader(), source.getClaims(), source.getMessage());
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

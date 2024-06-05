package io.ecp.testmall.utils;

import io.ecp.testmall.jwt.utils.JwtUtils;

public class tokenValidUtils {

    public static boolean tokenValid(String token) {
        String t = JwtUtils.extractToken(token);
        return !JwtUtils.validateToken(t);
    }
}

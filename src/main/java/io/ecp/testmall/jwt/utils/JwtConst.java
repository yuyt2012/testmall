package io.ecp.testmall.jwt.utils;

import org.springframework.beans.factory.annotation.Value;

public class JwtConst {

    @Value("${spring.jwt.secretKey}")
    public static String SECRET_KEY;
    public static final int ACCESS_EXP_TIME = 10;   // 10분
    public static final int REFRESH_EXP_TIME = 60 * 24;   // 24시간

    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_TYPE = "Bearer ";
}

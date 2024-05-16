package io.ecp.testmall.jwt.utils;

import org.springframework.beans.factory.annotation.Value;

public class JwtConst {

    public static final String SECRET_KEY = "1f83588b05ca0ad1354617ae5243ba2503bac3df183e79bfce6974e026442ccfef0ad8525bc7d7f2edeb7ec3be42739678b41f12ce4505ed2408651c8d0b3b14";
    public static final int ACCESS_EXP_TIME = 10;   // 10분
    public static final int REFRESH_EXP_TIME = 60 * 24;   // 24시간

    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_TYPE = "Bearer ";
}

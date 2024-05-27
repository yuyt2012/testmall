package io.ecp.testmall.jwt.utils;

import io.ecp.testmall.jwt.exception.CustomExpireException;
import io.ecp.testmall.jwt.exception.CustomJwtException;
import io.ecp.testmall.member.entity.Member;
import io.ecp.testmall.member.entity.PrincipalDetail;
import io.ecp.testmall.member.entity.Role;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

public class JwtUtils {

    public static String secretKey = JwtConst.SECRET_KEY;

    public static String extractToken(String header) {
        return header.split(" ")[1];
    }

    public static String generateToken(Map<String, Object> valueMap, int validTime) {
        SecretKey key = null;
        try {
            key = Keys.hmacShaKeyFor(JwtUtils.secretKey.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return Jwts.builder()
                .setHeader(Map.of("typ", "JWT"))
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(validTime).toInstant()))
                .signWith(key)
                .compact();
    }

    public static Map<String, Object> extractClaims(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(JwtUtils.secretKey.getBytes(StandardCharsets.UTF_8));
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException expiredJwtException) {
            throw new CustomExpireException("Token is expired", expiredJwtException);
        } catch (Exception e) {
            throw new CustomJwtException("Invalid token");
        }
    }

    public static Authentication getAuthentication(String token) {
        Map<String, Object> claims = extractClaims(token);

        String email = (String) claims.get("email");
        String name = (String) claims.get("name");
        String role = (String) claims.get("role");
        Role userRole = Role.valueOf(role);

        Member member = Member.builder()
                .email(email)
                .name(name)
                .role(userRole)
                .build();
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(member.getRole().getName()));
        PrincipalDetail principalDetails = new PrincipalDetail(member, authorities);

        return new UsernamePasswordAuthenticationToken(principalDetails, "", authorities);
    }

    public static boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(JwtUtils.secretKey.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (ExpiredJwtException expiredJwtException) {
            // 토큰이 만료된 경우, false 반환
            return false;
        } catch (Exception e) {
            // 토큰이 유효하지 않은 경우, false 반환
            return false;
        }
    }

    public static boolean isTokenExpired(String token) {
        try {
            validateToken(token);
        } catch (Exception e) {
            return (e instanceof CustomExpireException);
        }
        return false;
    }

    public static long tokenRemainTime(Integer expireTime) {
        Date expireDate = new Date((long) expireTime * (1000));
        long remainMs = expireDate.getTime() - System.currentTimeMillis();
        return remainMs / (1000 * 60);
    }
}

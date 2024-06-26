package io.ecp.testmall.jwt.filter;

import com.google.gson.Gson;
import io.ecp.testmall.jwt.exception.CustomExpireException;
import io.ecp.testmall.jwt.exception.CustomJwtException;
import io.ecp.testmall.jwt.utils.JwtConst;
import io.ecp.testmall.jwt.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtVerifyFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String[] whitelist = {"/signup", "/login", "/login/oauth2/code/kakao", "/",
            "/product/**", "/categories", "/category", "/images/**"};

    private static void checkAuthorizationHeader(String header) {
        if (header == null) {
            throw new CustomJwtException("Authorization header is missing");
        } else if (!header.startsWith(JwtConst.JWT_TYPE)) {
            throw new CustomJwtException("Authorization header is not Bearer type");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        return PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(JwtConst.JWT_HEADER);

        try {
            checkAuthorizationHeader(authHeader);
            String token = JwtUtils.extractToken(authHeader);
            Authentication authentication = JwtUtils.getAuthentication(token);

            log.info("authentication: {}", authentication);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (AuthenticationException e) {
            // Use getWriter() to send the error message
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print("{\"message\":\"Unauthorized\"}");
            out.flush();
        }
    }
}

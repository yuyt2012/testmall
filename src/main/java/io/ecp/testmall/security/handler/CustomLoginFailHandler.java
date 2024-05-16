package io.ecp.testmall.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class CustomLoginFailHandler implements AuthenticationFailureHandler {

    private Logger log = LoggerFactory.getLogger(getClass());


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Gson gson = new Gson();
        String json = gson.toJson(Map.of("error", "login failed"));

        response.setContentType("application/json; charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.print(json);
        writer.close();
    }
}

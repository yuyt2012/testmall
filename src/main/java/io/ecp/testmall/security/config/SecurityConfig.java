package io.ecp.testmall.security.config;

import io.ecp.testmall.jwt.filter.JwtVerifyFilter;
import io.ecp.testmall.oauth2.handler.OAuth2LoginSuccessHandler;
import io.ecp.testmall.oauth2.service.OAuth2UserService;
import io.ecp.testmall.security.handler.CustomLoginFailHandler;
import io.ecp.testmall.security.handler.CustomLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private OAuth2UserService oAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public CustomLoginFailHandler customLoginFailHandler() {
        return new CustomLoginFailHandler();
    }

    @Bean
    public JwtVerifyFilter jwtVerifyFilter() {
        return new JwtVerifyFilter();
    }

    @Bean
    public OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler() {
        return new OAuth2LoginSuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable);
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().permitAll());
        http
                .addFilterBefore(jwtVerifyFilter(), UsernamePasswordAuthenticationFilter.class);
        http
                 .formLogin(AbstractHttpConfigurer::disable);
        http
                .oauth2Login(auth -> auth
                        .successHandler(oAuth2LoginSuccessHandler())
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService)));
        return http.build();
    }
}

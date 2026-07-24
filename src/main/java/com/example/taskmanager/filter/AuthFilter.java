package com.example.taskmanager.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri= request.getRequestURI();
        String method = request.getMethod();

        boolean isRegistration=uri.equals("/api/v1/users")&&method.equals("POST");
        boolean isSwagger=uri.startsWith("/v3/api-docs") || uri.startsWith("/swagger-ui");

        return uri.startsWith("/api/v1/auth")||uri.startsWith("/actuator/")||isSwagger||isRegistration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=extractToken(request);
        if (token==null){
            response.setStatus(401);
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
            return;
        }
        filterChain.doFilter(request,response);
    }

    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase("access_token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

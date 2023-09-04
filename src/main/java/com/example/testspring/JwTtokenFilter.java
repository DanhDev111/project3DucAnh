package com.example.testspring;

import com.example.testspring.services.JwTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwTtokenFilter extends OncePerRequestFilter {
    @Autowired
    JwTokenService jwTokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        // doc token tu header
        String token = resolveToken(httpServletRequest);

        // verify token
        if (token != null) {
            // co token roi thi lay username, gọi db len user
            String username = jwTokenService.getUserName(token);
            if (username != null) {
                Authentication auth = jwTokenService.getAuthentication(username);
                // set vao context de co dang nhap roi
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    // lay token tu request gui len: header, params, form
    public String resolveToken(HttpServletRequest req) {
        // check postman header
        String bearerToken = req.getHeader("Authorization");
        System.out.println(bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}


package com.example.ApplicantCourseApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final SecurityContextAccessor contextAccessor;

    @Autowired
    public JwtAuthenticationFilter(final JwtService jwtService, final SecurityContextAccessor contextAccessor) {
        this.jwtService = jwtService;
        this.contextAccessor = contextAccessor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) {

            contextAccessor.setAuthentication(null);
        } else {
            if (!isEmpty(token) && jwtService.isTokenValid(token)) {
                final String userEmail = jwtService.getUserEmailFromToken(token);
                final Integer userId = jwtService.getUserIdFromToken(token);
                final Boolean isAdmin = jwtService.getIsAdminFromToken(token);
                if (userEmail != null && userId != null && isAdmin != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userEmail, null, Collections.emptyList());
                    authentication.setDetails(new UserDetails(userId, userEmail, isAdmin));
                    contextAccessor.setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}

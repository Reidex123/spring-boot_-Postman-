package com.spring.exercise;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {

        // Extract Authorization header
        String authHeader = request.getHeader("Authorization");

        //If there's no token or wrong format; skip (let other filters handle it)
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        //Extract token (remove "Bearer" prefix)
        String token = authHeader.substring(7);

        // Extract username from token
        String username = jwtService.extractUsername(token);

        // valid username and no authorization set yet
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //load user from DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate token
            if (jwtService.isTokenValid(token, userDetails)) {
                //set authentication in Spring Security context
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue to next filter
        filterChain.doFilter(request, response);
    }
}

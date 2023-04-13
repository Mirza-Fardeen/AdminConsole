package com.example.AdminConsole.filter;

import com.example.AdminConsole.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public JWTAuthorizationFilter(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header==null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        final String token = header.substring("Bearer ".length());
        final String subject = jwtTokenProvider.getSubject(token);
        System.out.println("Subject : "+subject);

        if(jwtTokenProvider.isTokenValid(subject,token)){
            final List<GrantedAuthority> authoritiesFromString = jwtTokenProvider.getAuthoritiesFromString(token);
            final UsernamePasswordAuthenticationToken authentication = jwtTokenProvider.getAuthentication(subject, authoritiesFromString, request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request,response);
    }
}

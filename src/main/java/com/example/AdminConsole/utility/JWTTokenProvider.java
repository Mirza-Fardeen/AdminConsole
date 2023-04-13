package com.example.AdminConsole.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.AdminConsole.service.AdminConsoleUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTTokenProvider {
    //"dhjhfjshfuejjgtgtjbk245dwuuik"
    public static final String secret = "dhjhfjshfuejjgtgtjbk245dwuuik";
    public String generateToken(AdminConsoleUserDetails employeeUserDetails){
       return JWT.create()
                .withIssuer("SpringJWTTest")
                .withAudience("SpringJWTTest users")
               .withSubject(employeeUserDetails.getUsername())
               .withArrayClaim("authorities",
                       getClaimsFromUser(employeeUserDetails))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() +1_000_000))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    private String[] getClaimsFromUser(AdminConsoleUserDetails employeeUserDetails) {
        List<String> claims = new ArrayList<>();
        employeeUserDetails.getAuthorities().stream().forEach(e-> claims.add(e.getAuthority()));
        return claims.toArray(new String[0]);
    }

    public List<GrantedAuthority> getAuthoritiesFromString(String token){
       return Arrays.stream(getClaimsFromToken(token)).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    private String[] getClaimsFromToken(String token){
        final JWTVerifier verifier = getVerifier();
       return verifier.verify(token).getClaim("authorities").asArray(String.class);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String username , List<GrantedAuthority> authorities , HttpServletRequest request){
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, authorities);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }
    public boolean isTokenValid(String username , String token){
        final JWTVerifier verifier = getVerifier();
      return  StringUtils.isNotEmpty(username) && !verifier.verify(token).getExpiresAt().before(new Date());
    }

    public String getSubject(String token){
      JWTVerifier jwtVerifier= getVerifier();
      return jwtVerifier.verify(token)
              .getSubject();
    }

    private JWTVerifier getVerifier() {
        final Algorithm algorithm = Algorithm.HMAC512(secret);
        final JWTVerifier springJWTTest = JWT.require(algorithm)
                .withIssuer("SpringJWTTest")
                .build();
        return springJWTTest;
    }
}

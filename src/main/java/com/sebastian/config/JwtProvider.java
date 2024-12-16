package com.sebastian.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

SecretKey key=Keys.hmacShaKeyFor(JWT_CONSTANT.SECRET_KEY.getBytes());

public String generateToken(Authentication auth) {
    Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
    authorities.forEach(authority -> {
        System.out.println("Authority: " + authority.getAuthority());
    });

    String roles = populateAuthorities(authorities);


    // Generowanie tokena JWT
    return Jwts.builder()
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + 86400000)) // Ważność: 1 dzień
        .claim("email", auth.getName()) // E-mail
        .claim("authorities", roles) // Role
        .signWith(key) // Podpisanie kluczem
        .compact();
}



    public String getEmailFromJwtToken(String jwt){
        jwt=jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().
        parseClaimsJws(jwt).getBody();

        return String.valueOf(claims.get("email"));
    }
    
    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();

        for ( GrantedAuthority authority:authorities){
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }

}

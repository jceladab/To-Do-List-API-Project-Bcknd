package com.example.ToDo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Jwt {

    @Value("${jwt.secret}")
    private String SECRET_KEY;


    //método que genera el token
    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    //método que valida el token JWT
    public boolean validateToken(String token, String email){
        return email.equals(extractEmail(token)) && !isTokenExpire(token);
    }

    //método que extrae el correo del token
    public String extractEmail(String token){
        return extractClaims(token).getSubject();
    }

    //método que verifica si el token ya expiró
    public boolean isTokenExpire(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

    //método que obtiene los datos del token
    public Claims extractClaims(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

}

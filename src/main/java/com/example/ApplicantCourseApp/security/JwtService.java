package com.example.ApplicantCourseApp.security;

import com.example.ApplicantCourseApp.exception.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;


    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.prefix}")
    private String tokenPrefix;

    public final static String CLAIM_USER_ID = "userId";
    public final static String CLAIM_IS_ADMIN = "isAdmin";

    public String createToken(final Long id, final String userEmail, final Boolean isAdmin) {
        return Jwts.builder()
                .setSubject(userEmail)
                .claim(CLAIM_USER_ID, id)
                .claim(CLAIM_IS_ADMIN, isAdmin)
                .setExpiration(createExpirationDate())
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    private Date createExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000L);
    }

    public String getUserEmailFromToken(final String token)  {
        try {
            final Claims claims = getClaimsFromToken(token);
            return claims.getSubject();
        } catch (Exception e) {
            throw new InvalidJwtException("Unable to parse token", e);
        }
    }

    Integer getUserIdFromToken(final String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            return Integer.parseInt((claims.get(CLAIM_USER_ID)).toString());
        } catch (Exception e) {
            throw new InvalidJwtException("Unable to parse token", e);
        }
    }

    Boolean getIsAdminFromToken(final String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            return Boolean.parseBoolean((claims.get(CLAIM_IS_ADMIN)).toString());
        } catch (Exception e) {
            throw new InvalidJwtException("Unable to parse token", e);
        }
    }

    Date getExpirationDateFromToken(final String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            return claims.getExpiration();
        } catch (Exception e) {
            throw new InvalidJwtException("Unable to parse token", e);
        }
    }

    boolean isTokenValid(final String token) {
        return !isTokenExpired(token);
    }


    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Claims getClaimsFromToken(final String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(cleanToken(token))
                    .getBody();
        } catch (Exception e) {
            throw new InvalidJwtException("Unable to parse token", e);
        }
    }

    private String cleanToken(final String token) {
        return token.replace(tokenPrefix, "").trim();
    }
}

package org.leo.moonpool.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

public class JwtConfig {

    private static String key = "dlrjtdmsqlalfqjsghdlqslekwjfeoahtakwcnfrjf";

    public static String generateToken(Map<String,Object> value, Integer min){
        SecretKey key = null;
        try {
            key = Keys.hmacShaKeyFor(JwtConfig.key.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e){
            throw new RuntimeException(e.getMessage());
        }
        String jwtStr = Jwts.builder()
                .setHeader(Map.of("type","Jwt"))
                .setClaims(value)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();
        return jwtStr;
    }
    public static Map<String, Object> validateToken(String token){
        Map<String, Object> claim =null;
        try {
            SecretKey key = Keys.hmacShaKeyFor(JwtConfig.key.getBytes("UTF-8"));
            claim = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException malformedJwtException){
            throw new JwtException("Malformed");
        } catch (ExpiredJwtException expiredJwtException){
            throw new JwtException("Expired");
        } catch (InvalidClaimException invalidClaimException){
            throw new JwtException("Invalid");
        } catch (JwtException jwtException) {
            throw new JwtException("JwtError");
        } catch (Exception e){
            throw new JwtException("Error");
        }
        return claim;
    }
}

package procentaurus.projects.ReservationSystem.Configuration.Auth.Jwt;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtService implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 20; // 20 min

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    public String extractUsername(String token)throws MalformedJwtException, ExpiredJwtException {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractIssuedAtDate(String token)throws MalformedJwtException, ExpiredJwtException {
        return extractClaim(token, Claims::getIssuedAt);
    }

    public Date extractExpirationDate(String token) throws MalformedJwtException, ExpiredJwtException {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws MalformedJwtException, ExpiredJwtException{
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws MalformedJwtException, ExpiredJwtException {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Boolean isTokenExpired(String token) throws MalformedJwtException{

        try {
            final Date expiration = extractExpirationDate(token);
        }catch(ExpiredJwtException e){
            return true;
        }
        return false;
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String username;
        try {
            username = extractUsername(token);
        }catch (ExpiredJwtException | MalformedJwtException e) {
            return false;
        }

        boolean isTokenExpired;
        try{
            isTokenExpired = isTokenExpired(token);
        }catch(MalformedJwtException e){
            return false;
        }

        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }
}
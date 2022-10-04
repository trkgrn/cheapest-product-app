package com.trkgrn_theomer.cheapspring.config.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtUtil {
    // hash işlemi yaparken kullanılacak key
    private String SECRET_KEY = "cozef";

    // verilen token a ait kullanıcı adını döndürür.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // verilen token a ait token bitiş süresini verir.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public long tokenExpiredHours(String token){
        long endTime = extractExpiration(token).getTime();
        long currentTime = new Date().getTime();
        long expiryTime = TimeUnit.MILLISECONDS.toHours(endTime-currentTime); // Tokenin kalan süresi (dakika cinsinden)
        return expiryTime;
    }


    public long tokenExpiredSeconds(String token){
        long endTime = extractExpiration(token).getTime();
        long currentTime = new Date().getTime();
        long expiryTime = TimeUnit.MILLISECONDS.toSeconds(endTime-currentTime); // Tokenin kalan süresi (saniye cinsinden)
        return expiryTime;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // verilen token a ait claims bilgisini alır.
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // token ın geçerlilik süre doldu mu?
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // userDetails objesini alır. createToken metoduna gönderir.
    public String generateToken(UserDetails userDetails,Long expireTime) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(),expireTime);
    }

    private String createToken(Map<String, Object> claims, String subject,Long expireTime) {
        return Jwts.builder().setClaims(claims)
                .setSubject(subject) // ilgili kullanıcı
                .setIssuedAt(new Date(System.currentTimeMillis())) // başlangıç
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(expireTime))) // bitiş
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // kullanılan algoritma ve bu algoritma çalışırken kullanılacak hash key değeri
                .compact();
    }

    private void removeToken(){

    }

    // token hala geçerli mi? kullanıcı adı doğru ise ve token ın geçerlilik süresi devam ediyorsa true döner.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}

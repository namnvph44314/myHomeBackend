package com.example.myschool.config.auth_config;

import com.example.myschool.commons.data.model.user.UserAthorDto;
import com.example.myschool.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.myschool.commons.core.json.JsonObject.mapFrom;
import static com.example.myschool.config.PlayloadJwt.USER_INFO;

@Component
public class JwtUtil {
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;
//    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;
    private static final long EXPIRE_DURATION =5 * 60 * 1000;

    public String generateToken(User user) {
        UserAthorDto userDetail = new UserAthorDto();
        userDetail.setUserId(user.getId());
        userDetail.setUsername(user.getPhoneNumber());

        Map<String, Object> map = new HashMap<>();
        map.put(USER_INFO, mapFrom(userDetail).encode());
        return Jwts.builder()
                .setSubject(mapFrom(userDetail).encode())
                .setNotBefore(new Date(System.currentTimeMillis()))
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String refereshToken(String token){
        final Claims claims=parseClaims(token);
        final long EXPIRATION=1*24*60*60*1000;
        Date now=new Date();
        Date expiration=new Date(now.getTime()+EXPIRATION);

        Claims newClaims=Jwts.claims()
                .setSubject(claims.getSubject())
                .setExpiration(expiration)
                .setIssuedAt(now)
                .setId(claims.getId());

        claims.forEach(newClaims::put);

        return Jwts.builder()
                .setClaims(newClaims)
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY.getBytes())
                .compact();
    }

    public Claims parseClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}

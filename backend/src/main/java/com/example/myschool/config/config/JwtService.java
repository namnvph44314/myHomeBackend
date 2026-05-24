package com.example.myschool.config.config;

import com.example.myschool.commons.core.json.JsonObject;
import com.example.myschool.config.ShortJwtPlayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.myschool.config.PlayloadJwt.USER_INFO;
import static java.util.stream.Collectors.toList;

@Service
public class JwtService {
    public final JwtConfig jwtConfig;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public AbstractAuthenticationToken extractAuthentication(String token, HttpServletRequest request,
                                                             HttpServletResponse response) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(body);
            String userString = (String) body.get(USER_INFO);
            ShortJwtPlayload userInfo = new JsonObject(userString).mapTo(ShortJwtPlayload.class);
            System.out.println("user_string: " + userString);
            System.out.println("user_info: " + userInfo);
            return new UsernamePasswordAuthenticationToken
                    (userInfo.getUserId(), userInfo, getAuthorities(userInfo));
        } catch (Exception e) {
            return null;
        }
    }

    private List<SimpleGrantedAuthority> getAuthorities(ShortJwtPlayload userInfo) {
        return Optional.ofNullable(userInfo.getRoles())
                .orElse(Collections.emptyList())
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.toUpperCase()))
                .collect(toList());
    }
}

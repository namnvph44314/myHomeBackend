package com.example.my_school.config.config;

import com.example.my_school.commons.core.json.JsonObject;
import com.example.my_school.config.ShortJwtPlayload;
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

import static com.example.my_school.config.PlayloadJwt.USER_INFO;
import static java.util.stream.Collectors.toList;

@Service
public class JwtService {
    private final JwtConfig jwtConfig;


    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    public String generateToken(UserDetails userDetails) {
//        return generateToken(new HashMap<>(), userDetails);
//    }
//
//    public String generateToken(
//            Map<String, Object> extraClaims,
//            UserDetails userDetails
//    ) {
//        return buildToken(extraClaims, userDetails, jwtConfig.getJwtExpiration());
//    }
//
//    public String generateRefreshToken(
//            UserDetails userDetails
//    ) {
//        return buildToken(new HashMap<>(), userDetails, jwtConfig.getRefreshExpiration());
//    }
//
//    private String buildToken(
//            Map<String, Object> extraClaims,
//            UserDetails userDetails,
//            long expiration
//    ) {
//        return Jwts
//                .builder()
//                .setClaims(extraClaims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignInKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Key getSignInKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecretKey());
//        return Keys.hmacShaKeyFor(keyBytes);
//    }


    public AbstractAuthenticationToken extractAuthentication(String token, HttpServletRequest request,
                                                             HttpServletResponse response) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();
            String userString = (String) body.get(USER_INFO);
            ShortJwtPlayload userInfo = new JsonObject(userString).mapTo(ShortJwtPlayload.class);
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

//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @NotNull
//    public UserJwtModel generateAccessToken(JwtPayloadInfo jwtPayloadInfo) {
//        UserJwtModel userJwt = new UserJwtModel();
//        final String longToken = generateLongJwt(jwtPayloadInfo);
//        userJwt.setLongToken(longToken);
//        final String shortToken = generateShortJwt(jwtPayloadInfo);
//        userJwt.setShortToken(shortToken);
//        try {
//            userJwt.setPayload(objectMapper.writeValueAsString(jwtPayloadInfo.getPayload()));
//        } catch (JsonProcessingException e) {
//        }
//        return userJwt;
//    }
//
//    public String generateLongJwt(JwtPayloadInfo jwtPayloadInfo) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put(USER_INFO, JsonObject.mapFrom(jwtPayloadInfo.getPayload()).encode());
//
//        return Jwts.builder()
//                .setSubject(String.valueOf(jwtPayloadInfo.getUserId()))
//                .setClaims(claims)
////            .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getRefreshExpiration()))
//                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getRefreshExpiration() + jwtConfig.getJwtExpiration()))
//                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey())
//                .compact();
//
//
//    }
//
//    public String generateShortJwt(JwtPayloadInfo jwtPayloadInfo) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put(USER_INFO, JsonObject.mapFrom(jwtPayloadInfo.getPayload()).encode());
//        return Jwts.builder()
//                .setSubject(String.valueOf(jwtPayloadInfo.getUserId()))
//                .setClaims(claims)
//                .setNotBefore(new Date(System.currentTimeMillis()))
////            .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getRefreshExpiration()))
//                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getRefreshExpiration()))
//                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey())
//                .compact();
//    }
}

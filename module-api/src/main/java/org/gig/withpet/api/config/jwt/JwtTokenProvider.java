package org.gig.withpet.api.config.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private static final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 30;
    private static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 7;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";

    @Value("${JWT.TOKEN.KEY}")
    private String key;

    public Boolean checkAuthorization(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION) == null;
    }

    public String resolve(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);

        return tokenParsing(token);
    }

    public String tokenParsing(String token) {
        return token.replace(BEARER + " ", "");
    }

    public String createAccessToken(String uid, List<String> roles) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(uid)
                .claim("roles", roles)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String createRefreshToken(String uid, List<String> roles) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(uid)
                .claim("roles", roles)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Boolean validate(String token, HttpServletRequest request) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .after(new Date());
            return true;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = (Claims) Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("roles").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
}

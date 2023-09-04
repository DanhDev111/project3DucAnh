package com.example.testspring.services;

import com.example.testspring.TestSpringApplication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

@Service
public class JwTokenService {
    //Thayv vif minh fix hard trong code thi minh
    @Value("${jwt.secret}")
    private String secretKey;
    @Autowired
    UserDetailsService userDetailsService;
    private long validity = 3600000; //1 h

    public String createToken(String username) {

        Claims claims = Jwts.claims().setSubject(username);
//        claims.put(username,claims);
        Date now = new Date();
        Date expire = new Date(now.getTime() + validity);

        String accessToken = Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return  accessToken;

    }

    //Viết hàm check xem token còn hay không
    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            //do nothing
        }
        return false;
    }

    public String getUserName(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            // do nothing
            e.printStackTrace();
            return null;
        }
    }
    public Authentication getAuthentication(String username) {
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }
}

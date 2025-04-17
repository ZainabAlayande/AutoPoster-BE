package com.example.autoposter.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.Claim;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Map;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public Map<String, Claim> extractClaimsFromToken(String token)  {
        DecodedJWT decodedJwt = validateToken(token);
        return decodedJwt.getClaims();
    }

    public DecodedJWT validateToken(String token){
        logger.info("This is the token " + token);
        return JWT.require(Algorithm.HMAC512(secret.getBytes()))
                .build().verify(token);
    }

    public String generateAccessToken(String email) {
        return JWT.create().withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(86000L))
                .withClaim("email", email)
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }


    public String generateRefreshToken(String email) {
        return JWT.create().withIssuedAt(Instant.now()).withExpiresAt(Instant.now()
                        .plusSeconds(86000L))
                .withClaim("email", email)
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }


}








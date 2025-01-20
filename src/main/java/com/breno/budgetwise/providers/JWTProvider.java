package com.breno.budgetwise.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class JWTProvider {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt.issuer}")
    private String ISSUER;

    @Value("${jwt.expire.in}")
    private int EXPIRES_IN;

    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    public DecodedJWT validateToken(String token) {

        token = token.replace("Bearer ", "");


        try {

            return JWT.require(algorithm)
                    .build()
                    .verify(token);


        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Expired token or Invalid");
        }
    }

    public String encodeJWT(UUID id) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(creationDate())
                .withExpiresAt(expirationDate())
                .withSubject(String.valueOf(id))
                .withClaim("userId", String.valueOf(id))
                .sign(algorithm);
    }

    public Instant getExpirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(EXPIRES_IN).toInstant();
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(EXPIRES_IN).toInstant();
    }

}

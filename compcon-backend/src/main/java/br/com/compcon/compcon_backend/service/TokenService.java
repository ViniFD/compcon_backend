package br.com.compcon.compcon_backend.service;

import br.com.compcon.compcon_backend.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        SecretKey secretKey = getSigningKey();
        Instant expirationTime = generateExpirationTime();

        return Jwts.builder()
                .issuer("CompCon API") // Define quem emitiu o token
                .subject(user.getEmail()) // Define o "dono" do token
                .issuedAt(new Date()) // Define a data de emissão
                .expiration(Date.from(expirationTime)) // Define a data de expiração
                .signWith(secretKey) // Assina o token com a chave
                .compact(); // Constrói e retorna o token
    }

    public String validateToken(String token) {
        try {
            SecretKey secretKey = getSigningKey();

            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getSubject();
        } catch (Exception e) {
            return "";
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Instant generateExpirationTime() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}

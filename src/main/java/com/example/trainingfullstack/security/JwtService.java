package com.example.trainingfullstack.security;

import com.example.trainingfullstack.exception.AppException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Service
public class JwtService {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final Long expirationMs;

    public JwtService(
            @Value("${jwt.private-key}") Resource privateKeyResource,
            @Value("${jwt.public-key}") Resource publicKeyResource,
            @Value("${jwt.expiration-ms}") Long expirationMs
    ) {
        this.privateKey = readPrivateKey(privateKeyResource);
        this.publicKey = readPublicKey(publicKeyResource);
        this.expirationMs = expirationMs;
    }

    private PrivateKey readPrivateKey(Resource resource){
        try{
            String pem = resource.getContentAsString(StandardCharsets.UTF_8)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(pem);

            return KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        } catch (Exception exception){
            throw new RuntimeException(
                    "Could not load JWT private key",
                    exception
            );
        }
    }
    private PublicKey readPublicKey(Resource resource){
        try {
            String pem = resource.getContentAsString(StandardCharsets.UTF_8)
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(pem);

            return KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(keyBytes));
    } catch (Exception exception){
            throw new RuntimeException(
                    "Could not load JWT public key",
                    exception
            );
        }
    }
    private Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean isValidToken(String token, UserDetails userDetails){
        try {
            Claims claims = extractClaims(token);

            return claims.getSubject()
                    .equals(userDetails.getUsername())
                    && claims.getExpiration().after(new Date());
        } catch (JwtException exception) {
            return false;
        }
    }
    public String generateToken(UserDetails userDetails){
        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + expirationMs);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim(
                        "roles",
                        userDetails.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority).filter(Objects::nonNull)
                                .map(role -> role.replaceFirst("^ROLE_", ""))
                                .toList()
                )
                .issuedAt(issuedAt)
                .expiration(expiredAt)
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }
    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }
    public long getExpirationSeconds() {
        return expirationMs / 1000;
    }
}

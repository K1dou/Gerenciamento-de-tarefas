package com.kidou.aplicativo.de.gerenciamento.de.tarefas.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;





    public String generateToken(Usuario usuario) {

        List<String> roles = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("api-gerenciamento-tarefas")
                    .withSubject(usuario.getUsername())
                    .withClaim("userId",usuario.getId())
                    .withClaim("roles",roles)
                    .withExpiresAt(expires())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            return e.getMessage();
        }
    }
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("api-gerenciamento-tarefas")
                    .build()
                    .verify(token)

                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }
    private Instant expires() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }

}

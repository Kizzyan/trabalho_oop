package com.faculdade.oop.util;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {
  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(UsuarioModel usuario) {
    Date agora = new Date();
    Date validade = new Date(agora.getTime() + 3_600_000);
    return JWT.create()
        .withIssuer(usuario.getLogin())
        .withIssuedAt(agora)
        .withExpiresAt(validade)

        .sign(Algorithm.HMAC256(secretKey));
  }

  public Authentication validarToken(String token) {
    Algorithm algoritmo = Algorithm.HMAC256(secretKey);
    JWTVerifier verifier = JWT.require(algoritmo).build();
    DecodedJWT decoded = verifier.verify(token);
    UsuarioModel usuario = new UsuarioModel(null, decoded.getIssuer(), null, null);
    return new UsernamePasswordAuthenticationToken(usuario, null,
        Collections.emptyList());
  }
}

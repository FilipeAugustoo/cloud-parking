package one.digitalinnovation.parking.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final Environment environment;
    public static final int TOKEN_EXPIRACAO = 1800000;

    public String gerarToken(UserDetailsData usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(environment.getProperty("token.password"));
            return JWT.create()
                    .withIssuer("API Voll.med")
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
                    .sign(algoritmo);

        } catch (JWTCreationException exception) {
            throw new RuntimeException("erro ao gerar token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(environment.getProperty("token.password"));
            return JWT.require(algoritmo)
                    .withIssuer("API Voll.med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}

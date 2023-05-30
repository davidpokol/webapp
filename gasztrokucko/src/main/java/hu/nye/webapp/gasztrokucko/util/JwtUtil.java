package hu.nye.webapp.gasztrokucko.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import hu.nye.webapp.gasztrokucko.configuration.JwtConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String USERNAME_CLAIM = "username";

    private final Algorithm algorithm;

    private final JwtConfigurationProperties jwtConfigurationProperties;


    @Autowired
    public JwtUtil(Algorithm algorithm, JwtConfigurationProperties jwtConfigurationProperties) {
        this.algorithm = algorithm;
        this.jwtConfigurationProperties = jwtConfigurationProperties;
    }

    public String createAndSignToken(String username) {
        return JWT.create()
                .withIssuer(jwtConfigurationProperties.getIssuer())
                .withClaim(USERNAME_CLAIM, username)
                .withExpiresAt(createExpirationDate())
                .sign(algorithm);
    }

    private Date createExpirationDate() {
        return Date.from(
                new Date()
                        .toInstant()
                        .plus(jwtConfigurationProperties.getTokenValidityInMinutes(),
                        ChronoUnit.MINUTES)
        );
    }
}

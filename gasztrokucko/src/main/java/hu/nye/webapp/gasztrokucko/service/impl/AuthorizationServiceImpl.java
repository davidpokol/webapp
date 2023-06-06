package hu.nye.webapp.gasztrokucko.service.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import hu.nye.webapp.gasztrokucko.service.AuthorizationService;
import hu.nye.webapp.gasztrokucko.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@AllArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final JwtUtil jwtUtil;

    @Override
    public Boolean validateTokenWithUsername(String token, String username) throws JWTVerificationException {
        String tokenUsername = jwtUtil.verifyAndDecodeToken(token);

        return username.equals(tokenUsername);
    }

    @Override
    public Boolean validateToken(String token) throws JWTVerificationException {
        String tokenUsername = jwtUtil.verifyAndDecodeToken(token);

        return Objects.nonNull(tokenUsername);
    }
}
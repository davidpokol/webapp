package hu.nye.webapp.gasztrokucko.service;

public interface AuthorizationService {

    Boolean validateTokenWithUsername(String token, String username);

    Boolean validateToken(String token);
}

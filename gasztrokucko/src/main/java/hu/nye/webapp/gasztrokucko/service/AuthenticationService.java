package hu.nye.webapp.gasztrokucko.service;

import hu.nye.webapp.gasztrokucko.request.AuthenticationRequest;

public interface AuthenticationService {
    public boolean authenticateUser(AuthenticationRequest authenticationRequest);
}

package hu.nye.webapp.gasztrokucko.controller;

import hu.nye.webapp.gasztrokucko.exception.UserAuthenticationException;
import hu.nye.webapp.gasztrokucko.request.AuthenticationRequest;
import hu.nye.webapp.gasztrokucko.service.impl.AuthenticationServiceImpl;
import hu.nye.webapp.gasztrokucko.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
public class AuthenticationController {

    private final JwtUtil jwtUtil;
    private final AuthenticationServiceImpl authenticationService;

    @PostMapping
    public String authenticate(@RequestBody AuthenticationRequest authenticationRequest) {

        if (!authenticationService.authenticateUser(authenticationRequest)) {
            throw new UserAuthenticationException("Authentication failed");
        }

        String username = authenticationRequest.getUsername();
        return jwtUtil.createAndSignToken(username);
    }
}

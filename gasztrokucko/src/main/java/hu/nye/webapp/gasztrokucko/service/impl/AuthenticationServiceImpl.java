package hu.nye.webapp.gasztrokucko.service.impl;

import hu.nye.webapp.gasztrokucko.model.entity.User;
import hu.nye.webapp.gasztrokucko.repository.UserRepository;
import hu.nye.webapp.gasztrokucko.request.AuthenticationRequest;
import hu.nye.webapp.gasztrokucko.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    @Override
    public boolean authenticateUser(AuthenticationRequest authenticationRequest) {

        String username = authenticationRequest.getUsername();
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()) {
            return false;
        }

        String password = authenticationRequest.getPassword();
        return user.get().getPassword().equals(password);
    }
}

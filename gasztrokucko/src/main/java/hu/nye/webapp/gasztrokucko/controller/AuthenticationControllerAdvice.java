package hu.nye.webapp.gasztrokucko.controller;


import hu.nye.webapp.gasztrokucko.exception.UserAuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = AuthenticationController.class)
public class AuthenticationControllerAdvice {

    @ExceptionHandler(value = UserAuthenticationException.class)
    public ResponseEntity<Void> authenticationHandler(UserAuthenticationException userAuthenticationException) {
        return ResponseEntity.status(401).build();
    }
}

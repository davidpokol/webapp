package hu.nye.webapp.gasztrokucko.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import hu.nye.webapp.gasztrokucko.exception.AuthorizationException;
import hu.nye.webapp.gasztrokucko.exception.InvalidUserRequestException;
import hu.nye.webapp.gasztrokucko.exception.UserNotFoundException;
import hu.nye.webapp.gasztrokucko.response.BadRequestError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = UserController.class)
public class UserControllerAdvice {

    @ExceptionHandler(value = InvalidUserRequestException.class)
    public ResponseEntity<BadRequestError> invalidUserHandler(InvalidUserRequestException invalidUserRequestException) {
        BadRequestError badRequestError = new BadRequestError(invalidUserRequestException.getErrors());

        return ResponseEntity.badRequest()
                .body(badRequestError);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Void> userNotFoundHandler(UserNotFoundException userNotFoundException) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = AuthorizationException.class)
    public ResponseEntity<Void> authorizationHandler(AuthorizationException authorizationException) {
        return ResponseEntity.status(403).build();
    }

    @ExceptionHandler(value = JWTVerificationException.class)
    public ResponseEntity<Void> jwtVerificationHandler(JWTVerificationException jwtVerificationException) {
        return ResponseEntity.status(401).build();
    }
}

package hu.nye.webapp.gasztrokucko.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import hu.nye.webapp.gasztrokucko.exception.AuthorizationException;
import hu.nye.webapp.gasztrokucko.exception.FileNotFoundException;
import hu.nye.webapp.gasztrokucko.exception.InvalidRecipeRequestException;
import hu.nye.webapp.gasztrokucko.exception.RecipeNotFoundException;
import hu.nye.webapp.gasztrokucko.response.BadRequestError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = RecipeController.class)
public class RecipeControllerAdvice {

    @ExceptionHandler(value = InvalidRecipeRequestException.class)
    public ResponseEntity<BadRequestError> invalidRequestHandler(InvalidRecipeRequestException invalidRecipeRequestException) {
        BadRequestError badRequestError = new BadRequestError(invalidRecipeRequestException.getErrors());

        return ResponseEntity.badRequest()
                .body(badRequestError);
    }

    @ExceptionHandler(value = RecipeNotFoundException.class)
    public ResponseEntity<Void> recipeNotFoundHandler(RecipeNotFoundException recipeNotFoundException) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = FileNotFoundException.class)
    public ResponseEntity<Void> fileNotFoundHandler(FileNotFoundException fileNotFoundException) {
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

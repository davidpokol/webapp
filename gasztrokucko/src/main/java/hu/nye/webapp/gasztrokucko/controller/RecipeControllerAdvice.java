package hu.nye.webapp.gasztrokucko.controller;

import hu.nye.webapp.gasztrokucko.exception.InvalidRecipeRequestException;
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

    // TODO: implement RecipeNotFoundException
}

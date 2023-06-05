package hu.nye.webapp.gasztrokucko.controller;

import hu.nye.webapp.gasztrokucko.exception.FileNotFoundException;
import hu.nye.webapp.gasztrokucko.exception.InvalidRecipeRequestException;
import hu.nye.webapp.gasztrokucko.exception.RecipeNotFoundException;
import hu.nye.webapp.gasztrokucko.response.BadRequestError;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

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

    /*@ExceptionHandler(value = NonTransientDataAccessException.class)
    public ResponseEntity<BadRequestError> uniqueConstraintHandler () {

        BadRequestError badRequestError = new BadRequestError(
                List.of("recipe name is already taken.")
        );

        return ResponseEntity.status(409).body(badRequestError);
    }*/
}

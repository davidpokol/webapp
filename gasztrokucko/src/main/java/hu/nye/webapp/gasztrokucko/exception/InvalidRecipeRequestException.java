package hu.nye.webapp.gasztrokucko.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidRecipeRequestException extends RuntimeException {

    private final List<String> errors;
    public InvalidRecipeRequestException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

}

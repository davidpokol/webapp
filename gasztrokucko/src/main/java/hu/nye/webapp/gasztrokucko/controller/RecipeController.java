package hu.nye.webapp.gasztrokucko.controller;

import hu.nye.webapp.gasztrokucko.dto.RecipeDTO;
import hu.nye.webapp.gasztrokucko.exception.InvalidRecipeRequestException;
import hu.nye.webapp.gasztrokucko.service.RecipeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeDTO>> findAll() {
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/{username}/recipes")
    public ResponseEntity<List<RecipeDTO>> findUserOwnRecipes(@PathVariable String username) {
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<RecipeDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<RecipeDTO> update(@PathVariable Long id, @Valid @RequestBody RecipeDTO recipeDTO, BindingResult bindingResult) {
        checkForRequestErrors(bindingResult);

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-recipe")
    public ResponseEntity<RecipeDTO> create(@Valid @RequestBody RecipeDTO recipeDTO, BindingResult bindingResult) {
        checkForRequestErrors(bindingResult);

        return ResponseEntity.ok().body(null);
    }

    @PostMapping("{username}/fav-recipe/{id}")
    public ResponseEntity<RecipeDTO> favorite(@PathVariable String username, @PathVariable Long id) {
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/{username}/fav-recipes")
    public ResponseEntity<RecipeDTO> findFavRecipes(@PathVariable String username) {
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("{username}/unfav-recipe/{id}")
    public ResponseEntity<RecipeDTO> unfavorite(@PathVariable String username, @PathVariable Long id) {
        return ResponseEntity.ok().body(null);
    }
    private void checkForRequestErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> messages = bindingResult.getFieldErrors()
                    .stream()
                    .map(this::fieldErrorToMessage)
                    .collect(Collectors.toList());

            throw new InvalidRecipeRequestException("Invalid recipe request ", messages);
        }
    }

    private String fieldErrorToMessage(FieldError fieldError) {
        return fieldError.getField() + " - " + fieldError.getDefaultMessage();
    }

}

package hu.nye.webapp.gasztrokucko.controller;

import hu.nye.webapp.gasztrokucko.exception.InvalidRecipeRequestException;
import hu.nye.webapp.gasztrokucko.model.dto.RecipeDTO;
import hu.nye.webapp.gasztrokucko.service.RecipeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> findAll() {
        List<RecipeDTO> recipes = recipeService.findAll();
        return ResponseEntity.ok().body(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> findById(@PathVariable Long id) {

        Optional<RecipeDTO> recipe = recipeService.findById(id);

        return recipe.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<RecipeDTO> create(@Valid @RequestBody RecipeDTO recipeDTO, BindingResult bindingResult) {
        checkForRequestErrors(bindingResult);

        RecipeDTO savedRecipe = recipeService.create(recipeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedRecipe);
    }

    @PutMapping("/update")
    public ResponseEntity<RecipeDTO> update(@Valid @RequestBody RecipeDTO recipeDTO, BindingResult bindingResult) {


        checkForRequestErrors(bindingResult);

        RecipeDTO updatedRecipe = recipeService.update(recipeDTO);

        return ResponseEntity.ok().body(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.delete(id);

        return ResponseEntity.noContent().build();
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

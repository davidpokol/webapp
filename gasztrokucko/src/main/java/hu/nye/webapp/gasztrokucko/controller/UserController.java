package hu.nye.webapp.gasztrokucko.controller;

import hu.nye.webapp.gasztrokucko.model.dto.RecipeDTO;
import hu.nye.webapp.gasztrokucko.model.dto.UserDTO;
import hu.nye.webapp.gasztrokucko.exception.InvalidUserRequestException;
import hu.nye.webapp.gasztrokucko.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        checkForRequestErrors(bindingResult);

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/{username}/fav-recipes")
    public ResponseEntity<RecipeDTO> findFavRecipes(@PathVariable String username) {
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/{username}/fav-recipes/{id}")
    public ResponseEntity<RecipeDTO> favorite(@PathVariable String username, @PathVariable Long id) {
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/{username}/fav-recipes/un-fav/{id}")
    public ResponseEntity<RecipeDTO> unfavorite(@PathVariable String username, @PathVariable Long id) {
        return ResponseEntity.ok().body(null);
    }

    private void checkForRequestErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> messages = bindingResult.getFieldErrors()
                    .stream()
                    .map(this::fieldErrorToMessage)
                    .collect(Collectors.toList());

            throw new InvalidUserRequestException("Invalid user request", messages);
        }
    }

    private String fieldErrorToMessage(FieldError fieldError) {
        return fieldError.getField() + " - " + fieldError.getDefaultMessage();
    }
}

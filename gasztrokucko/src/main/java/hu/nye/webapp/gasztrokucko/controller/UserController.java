package hu.nye.webapp.gasztrokucko.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import hu.nye.webapp.gasztrokucko.exception.AuthorizationException;
import hu.nye.webapp.gasztrokucko.exception.InvalidUserRequestException;
import hu.nye.webapp.gasztrokucko.model.dto.RecipeDTO;
import hu.nye.webapp.gasztrokucko.model.dto.UserDTO;
import hu.nye.webapp.gasztrokucko.service.AuthorizationService;
import hu.nye.webapp.gasztrokucko.service.UserService;
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
@RequestMapping("/users")
public class UserController {


    private final UserService userService;
    private final AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> findByUsername(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                  @PathVariable String username) throws JWTVerificationException {

        Optional<UserDTO> user = userService.findByUserName(username);

        if (user.isPresent() && !authorizationService.validateTokenWithUsername(token, username)) {
            throw new AuthorizationException("Access denied!");
        }

        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserDTO> signUp(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        checkForRequestErrors(bindingResult);
        UserDTO newUser;

        newUser = userService.create(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/{username}/recipes")
    public ResponseEntity<List<RecipeDTO>> findUserOwnRecipes(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                              @PathVariable String username)
            throws JWTVerificationException {

        if (userService.findByUserName(username).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!authorizationService.validateTokenWithUsername(token, username)) {
            throw new AuthorizationException("Access denied!");
        }

        List<RecipeDTO> ownRecipes = userService.findOwnRecipes(username);
        return ResponseEntity.ok().body(ownRecipes);
    }

    @GetMapping("/{username}/fav-recipes")
    public ResponseEntity<RecipeDTO> findFavRecipes(@PathVariable String username, @RequestBody Long recipeId) {

        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/{username}/fav-recipes/{id}")
    public ResponseEntity<UserDTO> favorite(@PathVariable String username, @PathVariable Long id) {

        UserDTO userDTO = userService.favRecipe(username, id);
        return ResponseEntity.ok().body(userDTO);
    }

    @PutMapping("/{username}/fav-recipes/un-fav/{id}")
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

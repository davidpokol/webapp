package hu.nye.webapp.gasztrokucko.controller;

import hu.nye.webapp.gasztrokucko.dto.UserDTO;
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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(null);
    }

    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        checkForRequestErrors(bindingResult);

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

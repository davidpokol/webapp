package hu.nye.webapp.gasztrokucko.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import hu.nye.webapp.gasztrokucko.exception.AuthorizationException;
import hu.nye.webapp.gasztrokucko.exception.FileNotFoundException;
import hu.nye.webapp.gasztrokucko.exception.InvalidRecipeRequestException;
import hu.nye.webapp.gasztrokucko.exception.RecipeNotFoundException;
import hu.nye.webapp.gasztrokucko.model.dto.FileDTO;
import hu.nye.webapp.gasztrokucko.model.dto.RecipeDTO;
import hu.nye.webapp.gasztrokucko.service.AuthorizationService;
import hu.nye.webapp.gasztrokucko.service.FileService;
import hu.nye.webapp.gasztrokucko.service.RecipeService;
import hu.nye.webapp.gasztrokucko.util.FileUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final FileService fileService;

    private final AuthorizationService authorizationService;
    private final FileUtil fileUtil;

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
    public ResponseEntity<RecipeDTO> create(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                            @Valid @RequestBody RecipeDTO recipeDTO,
                                            BindingResult bindingResult) throws JWTVerificationException {
        checkForRequestErrors(bindingResult);

        if (!authorizationService.validateToken(token)) {
            throw new AuthorizationException("Access denied!");
        }

        RecipeDTO savedRecipe = recipeService.create(recipeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedRecipe);
    }

    @PutMapping("/update")
    public ResponseEntity<RecipeDTO> update(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                            @Valid @RequestBody RecipeDTO recipeDTO,
                                            BindingResult bindingResult)
            throws AuthorizationException, JWTVerificationException {
        checkForRequestErrors(bindingResult);

        if (!authorizationService.validateTokenWithUsername(token, recipeDTO.getCreatedBy())) {
            throw new AuthorizationException("Access denied!");
        }

        RecipeDTO updatedRecipe = recipeService.update(recipeDTO);

        return ResponseEntity.ok().body(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                       @PathVariable Long id) throws JWTVerificationException {
        Optional<RecipeDTO> recipe = recipeService.findById(id);

        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException();
        }
        String createdBy = recipe.get().getCreatedBy();

        if (!authorizationService.validateTokenWithUsername(token, createdBy)) {
            throw new AuthorizationException("Access denied!");
        }
        recipeService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<?> uploadFile(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                        @PathVariable Long id,
                                        @RequestParam("photo")MultipartFile file)
            throws IOException, JWTVerificationException {

        /*
        Optional<RecipeDTO> recipe = recipeService.findById(id);

        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException();
        }
        String createdBy = recipe.get().getCreatedBy();

        if (!authorizationService.validateToken(token, createdBy)) {
            throw new AuthorizationException("Access denied!");
        }
        */

        String uploadFile = fileService.uploadFile(id, file);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(uploadFile);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?> downloadFile(@PathVariable("id") Long photoId) throws FileNotFoundException {
        Optional<FileDTO> fileData = fileService.downloadFile(photoId);

        if (fileData.isEmpty()) {
            throw new FileNotFoundException(String.format(
                    "File not found with ID: %s", photoId
            ));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(fileData.get().getContentType()))
                .body(fileUtil.decompressImage(fileData.get().getData()));
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

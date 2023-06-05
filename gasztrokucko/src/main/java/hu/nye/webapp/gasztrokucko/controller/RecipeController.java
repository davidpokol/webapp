package hu.nye.webapp.gasztrokucko.controller;

import hu.nye.webapp.gasztrokucko.exception.FileNotFoundException;
import hu.nye.webapp.gasztrokucko.exception.InvalidRecipeRequestException;
import hu.nye.webapp.gasztrokucko.model.dto.FileDTO;
import hu.nye.webapp.gasztrokucko.model.dto.RecipeDTO;
import hu.nye.webapp.gasztrokucko.response.RecipeResponse;
import hu.nye.webapp.gasztrokucko.service.FileService;
import hu.nye.webapp.gasztrokucko.service.RecipeService;
import hu.nye.webapp.gasztrokucko.util.FileUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
    private final FileUtil fileUtil;

    @GetMapping
    public ResponseEntity<List<RecipeResponse>> findAll() {
        List<RecipeResponse> recipes = recipeService.findAll();
        return ResponseEntity.ok().body(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> findById(@PathVariable Long id) {

        Optional<RecipeResponse> recipe = recipeService.findById(id);

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

    @PostMapping("/{id}/image")
    public ResponseEntity<?> uploadFile(@PathVariable("id") Long photoId, @RequestParam("photo")MultipartFile file) throws IOException {
        String uploadFile = fileService.uploadFile(photoId, file);
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

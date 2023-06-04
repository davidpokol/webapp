package hu.nye.webapp.gasztrokucko.service.impl;

import hu.nye.webapp.gasztrokucko.exception.FileParseException;
import hu.nye.webapp.gasztrokucko.exception.RecipeNotFoundException;
import hu.nye.webapp.gasztrokucko.model.dto.RecipeDTO;
import hu.nye.webapp.gasztrokucko.model.entity.File;
import hu.nye.webapp.gasztrokucko.model.entity.Recipe;
import hu.nye.webapp.gasztrokucko.model.entity.User;
import hu.nye.webapp.gasztrokucko.repository.RecipeRepository;
import hu.nye.webapp.gasztrokucko.repository.UserRepository;
import hu.nye.webapp.gasztrokucko.response.RecipeResponse;
import hu.nye.webapp.gasztrokucko.service.RecipeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final ModelMapper modelMapper;

    private final RecipeRepository recipeRepository;

    private final UserRepository userRepository;

    @Override
    public List<RecipeResponse> findAll() {

        List<Recipe> allRecipes = recipeRepository.findAll();
        List<RecipeResponse> resultList = new ArrayList<>();

        for (Recipe recipe: allRecipes) {

            String createdBy = recipe.getCreatedBy().getUsername();
            recipe.setCreatedBy(null);
            RecipeResponse map = modelMapper.map(recipe, RecipeResponse.class);
            map.setCreatedBy(createdBy);
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public Optional<RecipeResponse> findById(Long id) {

        Optional<Recipe> foundRecipe = recipeRepository.findById(id);

        if (foundRecipe.isPresent()) {
            Recipe unwrappedRecipe = foundRecipe.get();
            String createdBy = foundRecipe.get().getCreatedBy().getUsername();
            foundRecipe.get().setCreatedBy(null);
            RecipeResponse resultRecipe = modelMapper.map(unwrappedRecipe, RecipeResponse.class);
            resultRecipe.setCreatedBy(createdBy);
            return Optional.of(resultRecipe);
        }
        return Optional.empty();
    }

    @Override
    public RecipeDTO create(RecipeDTO recipeDTO) {

        String createdBy = recipeDTO.getCreatedBy();
        Optional<User> createdByObject = userRepository.findByUsername(createdBy);
        File photo = convertMultiPartFileToFile(recipeDTO.getPhoto());
        Recipe recipeToSave = new Recipe(
                null,
                recipeDTO.getName(),
                createdByObject.get(),
                recipeDTO.getLastModified(),
                recipeDTO.getRecipeModificationType(),
                recipeDTO.getCategory(),
                recipeDTO.getDifficulty(),
                recipeDTO.getIngredients(),
                recipeDTO.getInstructions(),
                photo,
                recipeDTO.getFavoritedBy()
                );

        Recipe savedRecipe = recipeRepository.save(recipeToSave);
        RecipeDTO returnRecipe = modelMapper.map(savedRecipe, RecipeDTO.class);
        returnRecipe.setCreatedBy(createdBy);
        return returnRecipe;
    }

    @Override
    public RecipeDTO update(RecipeDTO recipeDTO) {

        Long id = recipeDTO.getId();

        Optional<Recipe> recipeToUpdate = recipeRepository.findById(id);

        if(recipeToUpdate.isEmpty()) {
            throw new RecipeNotFoundException(
                    String.format("Recipe not found with id=%d", id)
            );
        }
        Optional<RecipeResponse> byId = findById(recipeDTO.getId());

        if (byId.isEmpty()) {
            throw new RecipeNotFoundException();
        } else if (!byId.get().getCreatedBy().equals(recipeDTO.getCreatedBy())) {
            throw new RecipeNotFoundException(
                    String.format("Recipe not found created by %s", recipeDTO.getCreatedBy())
            );
        }
        String createdBy = recipeDTO.getCreatedBy();
        Optional<User> createdByObject = userRepository.findByUsername(createdBy);
        File photo = convertMultiPartFileToFile(recipeDTO.getPhoto());
        recipeDTO.setCreatedBy(null);
        recipeDTO.setPhoto(null);
        Recipe recipeToPersist = modelMapper.map(recipeDTO, Recipe.class);
        recipeToPersist.setCreatedBy(createdByObject.get());
        recipeToPersist.setPhoto(photo);

        Recipe savedRecipe = recipeRepository.save(recipeToPersist);

        RecipeDTO map = modelMapper.map(savedRecipe, RecipeDTO.class);
        map.setCreatedBy(createdBy);
        return map;
    }

    @Override
    public void delete(Long id) {

        Optional<Recipe> recipeToDelete = recipeRepository.findById(id);

        if(recipeToDelete.isPresent()) {
            recipeRepository.delete(recipeToDelete.get());
        } else {
            throw new RecipeNotFoundException(
                    String.format("Recipe not found with id=%d",id)
            );
        }
    }

    private File convertMultiPartFileToFile(MultipartFile multipartFile) {
        File photo;
        try {
            photo = new File(null,
                    multipartFile.getName(),
                    multipartFile.getContentType(),
                    multipartFile.getSize(),
                    multipartFile.getBytes());
        } catch (IOException e) {
            throw new FileParseException("Failed to parse file");
        }

        return photo;
    }



}

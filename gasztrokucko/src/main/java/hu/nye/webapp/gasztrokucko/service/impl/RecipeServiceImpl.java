package hu.nye.webapp.gasztrokucko.service.impl;

import hu.nye.webapp.gasztrokucko.exception.RecipeNotFoundException;
import hu.nye.webapp.gasztrokucko.model.dto.RecipeDTO;
import hu.nye.webapp.gasztrokucko.model.entity.Recipe;
import hu.nye.webapp.gasztrokucko.model.entity.User;
import hu.nye.webapp.gasztrokucko.repository.RecipeRepository;
import hu.nye.webapp.gasztrokucko.repository.UserRepository;
import hu.nye.webapp.gasztrokucko.service.RecipeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public List<RecipeDTO> findAll() {

        List<Recipe> allRecipes = recipeRepository.findAll();
        List<RecipeDTO> resultList = new ArrayList<>();

        for (Recipe recipe: allRecipes) {

            String createdBy = recipe.getCreatedBy().getUsername();
            recipe.setCreatedBy(null);
            RecipeDTO map = modelMapper.map(recipe, RecipeDTO.class);
            map.setCreatedBy(createdBy);
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public Optional<RecipeDTO> findById(Long id) {

        Optional<Recipe> foundRecipe = recipeRepository.findById(id);

        if (foundRecipe.isPresent()) {
            Recipe unwrappedRecipe = foundRecipe.get();
            String createdBy = unwrappedRecipe.getCreatedBy().getUsername();
            unwrappedRecipe.setCreatedBy(null);
            RecipeDTO resultRecipe = modelMapper.map(unwrappedRecipe, RecipeDTO.class);
            resultRecipe.setCreatedBy(createdBy);
            return Optional.of(resultRecipe);
        }
        return Optional.empty();
    }

    @Override
    public RecipeDTO create(RecipeDTO recipeDTO) {

        String createdBy = recipeDTO.getCreatedBy();
        Optional<User> createdByObject = userRepository.findByUsername(createdBy);
        Recipe recipeToSave = new Recipe(
                null,
                recipeDTO.getName(),
                createdByObject.get(),
                recipeDTO.getLastModified(),
                recipeDTO.getRecipeModificationType(),
                recipeDTO.getCategory(),
                recipeDTO.getDifficulty(),
                recipeDTO.getIngredients(),
                recipeDTO.getInstructions()
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
        Optional<RecipeDTO> byId = findById(recipeDTO.getId());

        if (byId.isEmpty()) {
            throw new RecipeNotFoundException();
        } else if (!byId.get().getCreatedBy().equals(recipeDTO.getCreatedBy())) {
            throw new RecipeNotFoundException(
                    String.format("Recipe not found created by %s", recipeDTO.getCreatedBy())
            );
        }
        String createdBy = recipeDTO.getCreatedBy();
        Optional<User> createdByObject = userRepository.findByUsername(createdBy);
        recipeDTO.setCreatedBy(null);
        Recipe recipeToPersist = modelMapper.map(recipeDTO, Recipe.class);
        recipeToPersist.setCreatedBy(createdByObject.get());

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
}

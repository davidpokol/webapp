package hu.nye.webapp.gasztrokucko.service.impl;

import hu.nye.webapp.gasztrokucko.exception.RecipeNotFoundException;
import hu.nye.webapp.gasztrokucko.model.dto.RecipeDTO;
import hu.nye.webapp.gasztrokucko.model.dto.UserDTO;
import hu.nye.webapp.gasztrokucko.model.entity.Recipe;
import hu.nye.webapp.gasztrokucko.model.entity.User;
import hu.nye.webapp.gasztrokucko.repository.RecipeRepository;
import hu.nye.webapp.gasztrokucko.service.RecipeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final ModelMapper modelMapper;

    private final RecipeRepository recipeRepository;

    @Override
    public List<RecipeDTO> findAll() {

        return recipeRepository
                .findAll()
                .stream()
                .map(recipe -> modelMapper.map(recipe, RecipeDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public Optional<RecipeDTO> findById(Long id) {
        return recipeRepository.findById(id)
                .map(m -> modelMapper.map(m, RecipeDTO.class));
    }

    @Override
    public RecipeDTO create(RecipeDTO recipeDTO) {
        recipeDTO.setId(null);

        Recipe recipeToSave = modelMapper.map(recipeDTO, Recipe.class);
        Recipe savedRecipe = recipeRepository.save(recipeToSave);

        return modelMapper.map(savedRecipe, RecipeDTO.class);
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

        Recipe recipeToPersist = modelMapper.map(recipeDTO, Recipe.class);
        Recipe savedRecipe = recipeRepository.save(recipeToPersist);

        return modelMapper.map(savedRecipe, RecipeDTO.class);
    }

    @Override
    public void delete(Long id) {

        Optional<Recipe> recipeToDelete = recipeRepository.findById(id);

        if(recipeToDelete.isPresent()) {
            recipeRepository.delete(recipeToDelete.get());
        } else {
            throw new RecipeNotFoundException(
                    String.format("recipe not found with id=%d",id)
            );
        }
    }

}

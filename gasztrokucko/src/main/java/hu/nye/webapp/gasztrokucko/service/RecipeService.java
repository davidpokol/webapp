package hu.nye.webapp.gasztrokucko.service;

import hu.nye.webapp.gasztrokucko.dto.RecipeDTO;

import java.util.List;
import java.util.Optional;

public interface RecipeService {

    List<RecipeDTO> findAll();

    Optional<RecipeDTO> findByID(Long id);

    RecipeDTO create(RecipeDTO recipeDTO);

    RecipeDTO update(RecipeDTO recipeDTO);

    void delete(Long id);
}

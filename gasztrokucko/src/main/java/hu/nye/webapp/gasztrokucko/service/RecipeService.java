package hu.nye.webapp.gasztrokucko.service;

import hu.nye.webapp.gasztrokucko.model.dto.RecipeDTO;
import hu.nye.webapp.gasztrokucko.response.RecipeResponse;

import java.util.List;
import java.util.Optional;

public interface RecipeService {

    List<RecipeResponse> findAll();

    Optional<RecipeResponse> findById(Long id);

    RecipeDTO create(RecipeDTO recipeDTO);

    RecipeDTO update(RecipeDTO recipeDTO);

    void delete(Long id);
}

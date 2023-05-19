package hu.nye.webapp.gasztrokucko.service.impl;

import hu.nye.webapp.gasztrokucko.dto.RecipeDTO;
import hu.nye.webapp.gasztrokucko.repository.RecipeRepository;
import hu.nye.webapp.gasztrokucko.service.RecipeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final ModelMapper modelMapper;

    private final RecipeRepository recipeRepository;

    @Override
    public List<RecipeDTO> findAll() {
        return null;
    }

    @Override
    public Optional<RecipeDTO> findByID(Long id) {
        return Optional.empty();
    }

    @Override
    public RecipeDTO create(RecipeDTO recipeDTO) {
        return null;
    }

    @Override
    public RecipeDTO update(RecipeDTO recipeDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

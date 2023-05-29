package hu.nye.webapp.gasztrokucko.service;

import hu.nye.webapp.gasztrokucko.model.dto.RecipeDTO;
import hu.nye.webapp.gasztrokucko.model.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<UserDTO> findAll();

    public Optional<UserDTO> findByUserName(String username);

    public UserDTO create(UserDTO userDTO);

    public List<RecipeDTO> findOwnRecipes(String username);

    public UserDTO favRecipe(String username, Long recipeId);

    public UserDTO unfavRecipe(String username, Long recipeId);

    public List<RecipeDTO> findFavRecipes(String username);
}

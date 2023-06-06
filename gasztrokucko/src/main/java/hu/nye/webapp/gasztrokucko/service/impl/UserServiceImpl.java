package hu.nye.webapp.gasztrokucko.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import hu.nye.webapp.gasztrokucko.exception.RecipeNotFoundException;
import hu.nye.webapp.gasztrokucko.exception.UserNotFoundException;
import hu.nye.webapp.gasztrokucko.model.dto.RecipeDTO;
import hu.nye.webapp.gasztrokucko.model.dto.UserDTO;

import hu.nye.webapp.gasztrokucko.model.entity.Recipe;
import hu.nye.webapp.gasztrokucko.model.entity.User;
import hu.nye.webapp.gasztrokucko.repository.RecipeRepository;
import hu.nye.webapp.gasztrokucko.repository.UserRepository;
import hu.nye.webapp.gasztrokucko.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final RecipeRepository recipeRepository;

    @Override
    public List<UserDTO> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findByUserName(String username) {

        return userRepository.findByUsername(username)
                .map(m -> modelMapper.map(m, UserDTO.class));
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        userDTO.setId(null);

        User userToSave = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(userToSave);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public List<RecipeDTO> findOwnRecipes(String username) {

        List<RecipeDTO> result = new ArrayList<>();

        for (Recipe recipe: recipeRepository.findAll()) {
            if (recipe.getCreatedBy().getUsername().equals(username)) {

                recipe.setCreatedBy(null);
                RecipeDTO map = modelMapper.map(recipe, RecipeDTO.class);
                map.setCreatedBy(username);
                result.add(map);
            }
        }
        return result;
    }

    @Override
    public UserDTO favRecipe(String username, Long recipeId) {

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException(
                    String.format("User not found with name= %s", username));
        }

        Optional<Recipe> recipe = recipeRepository.findById(recipeId);

        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException(
                    String.format("Recipe not found with id= %d", recipeId));
        }

        User user1 = user.get();
        Set<Recipe> favRecipes = user1.getFavRecipes();
        favRecipes.add(recipe.get());
        user1.setFavRecipes(favRecipes);

        User save = userRepository.save(user1);

        return modelMapper.map(save, UserDTO.class);
    }

    @Override
    public UserDTO unfavRecipe(String username, Long recipeId) {

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException(
                    String.format("User not found with name= %s", username));
        }

        Optional<Recipe> recipe = recipeRepository.findById(recipeId);

        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException(
                    String.format("Recipe not found with id= %d", recipeId));
        }

        return null;
    }

    @Override
    public List<RecipeDTO> findFavRecipes(String username) {
        return null; //TODO
    }

}

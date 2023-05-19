package hu.nye.webapp.gasztrokucko.dto;

import hu.nye.webapp.gasztrokucko.model.entity.Recipe;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private Set<Recipe> favRecipes = new HashSet<>();
}

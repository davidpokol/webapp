package hu.nye.webapp.gasztrokucko.dto;

import hu.nye.webapp.gasztrokucko.model.entity.User;
import hu.nye.webapp.gasztrokucko.model.enums.Category;
import hu.nye.webapp.gasztrokucko.model.enums.Difficulty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeDTO {

    private Long id;

    @NotBlank
    private String name;

    private User createdBy;

    @NotBlank
    private Category category;

    @NotBlank
    private Difficulty difficulty;

    private List<@NotBlank String> ingredients;

    private String instructions;

    private byte[] photo;

    private Set<User> favoritedBy = new HashSet<>();

}

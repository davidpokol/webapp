package hu.nye.webapp.gasztrokucko.model.dto;

import hu.nye.webapp.gasztrokucko.model.entity.User;
import hu.nye.webapp.gasztrokucko.model.enums.recipe.Category;
import hu.nye.webapp.gasztrokucko.model.enums.recipe.Difficulty;
import hu.nye.webapp.gasztrokucko.model.enums.recipe.ModificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecipeDTO {

    private Long id;

    @NotBlank
    @Size(min = 5, message = "{validation.name.size.too_short}")
    @Size(max = 60, message = "{validation.name.size.too_long}")
    private String name;

    private String createdBy;

    @NotBlank
    @Size(min = 5, message = "{validation.name.size.too_short}")
    @Size(max = 50, message = "{validation.name.size.too_long}")
    private String lastModified;

    private ModificationType recipeModificationType;

    private Category category;

    private Difficulty difficulty;

    private List<@NotBlank
    @Size(max = 30, message = "{validation.name.size.too_long}")
            String> ingredients;

    @NotBlank
    private String instructions;

    private String photo;

    private Set<User> favoritedBy = new HashSet<>();

    private RecipeDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.createdBy = builder.createdBy;
        this.lastModified = builder.lastModified;
        this.recipeModificationType = builder.recipeModificationType;
        this.category = builder.category;
        this.difficulty = builder.difficulty;
        this.ingredients = builder.ingredients;
        this.instructions = builder.instructions;
        this.photo = builder.photo;
        this.favoritedBy = builder.favoritedBy;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecipeDTO recipeDTO = (RecipeDTO) o;

        return new EqualsBuilder()
                .append(id, recipeDTO.id)
                .append(name, recipeDTO.name)
                .append(createdBy, recipeDTO.createdBy)
                .append(lastModified, recipeDTO.lastModified)
                .append(recipeModificationType, recipeDTO.recipeModificationType)
                .append(category, recipeDTO.category)
                .append(difficulty, recipeDTO.difficulty)
                .append(ingredients, recipeDTO.ingredients)
                .append(instructions, recipeDTO.instructions)
                .append(photo, recipeDTO.photo)
                .append(favoritedBy, recipeDTO.favoritedBy)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(createdBy)
                .append(lastModified)
                .append(recipeModificationType)
                .append(category)
                .append(difficulty)
                .append(ingredients)
                .append(instructions)
                .append(photo)
                .append(favoritedBy)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("createdBy", createdBy)
                .append("lastModified", lastModified)
                .append("recipeModificationType", recipeModificationType)
                .append("category", category)
                .append("difficulty", difficulty)
                .append("ingredients", ingredients)
                .append("instructions", instructions)
                .append("photo", photo)
                .append("favoritedBy", favoritedBy)
                .toString();
    }

    @NoArgsConstructor
    public static final class Builder {
        private Long id;
        private String name;
        private String createdBy;

        private String lastModified;

        private ModificationType recipeModificationType;

        private Category category;

        private Difficulty difficulty;
        private List<String> ingredients;

        private String instructions;
        private String photo;
        private Set<User> favoritedBy = new HashSet<>();

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }
        public Builder withName(String name) {
            this.name = name;
            return this;
        }
        public Builder withCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }
        public Builder withLastModified(String lastModified) {
            this.lastModified = lastModified;
            return this;
        }
        public Builder withRecipeModificationType(ModificationType recipeModificationType) {
            this.recipeModificationType = recipeModificationType;
            return this;
        }
        public Builder withCategory(Category category) {
            this.category = category;
            return this;
        }
        public Builder withDifficulty(Difficulty difficulty) {
            this.difficulty = difficulty;
            return this;
        }
        public Builder withIngredients(List<String> ingredients) {
            this.ingredients = ingredients;
            return this;
        }
        public Builder withInstructions(String instructions) {
            this.instructions = instructions;
            return this;
        }
        public Builder withPhoto(String photo) {
            this.photo = photo;
            return this;
        }
        public Builder withFavoritedBy(Set<User> favoritedBy) {
            this.favoritedBy = favoritedBy;
            return this;
        }

        public RecipeDTO build() {
            return new RecipeDTO(this);
        }
    }
}

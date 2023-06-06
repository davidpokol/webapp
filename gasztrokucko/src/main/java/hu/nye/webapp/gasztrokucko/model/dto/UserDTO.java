package hu.nye.webapp.gasztrokucko.model.dto;

import hu.nye.webapp.gasztrokucko.model.entity.Recipe;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank
    @Size(min = 5, message = "length too short, min 5")
    @Size(max = 30, message = "length too long, max 30")
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 5, message = "length too short, min 5")
    private String password;

    private Set<Recipe> favRecipes = new HashSet<>();

    private UserDTO(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.favRecipes = builder.favRecipes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void addFavRecipe(Recipe favRecipe) {
        favRecipes.add(favRecipe);
    }

    public void removeFavRecipe(Recipe favRecipe) {
        favRecipes.remove(favRecipe);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDTO userDTO = (UserDTO) o;

        return new EqualsBuilder()
                .append(id, userDTO.id)
                .append(username, userDTO.username)
                .append(email, userDTO.email)
                .append(password, userDTO.password)
                .append(favRecipes, userDTO.favRecipes)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(username)
                .append(email)
                .append(password)
                .append(favRecipes)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id: ", id)
                .append("username: ", username)
                .append("email: ", email)
                .append("password: ", password)
                .append("favRecipes: ", favRecipes)
                .toString();
    }

    @NoArgsConstructor
    public static final class Builder {
        private Long id;

        private String username;

        private String email;

        private String password;

        private Set<Recipe> favRecipes = new HashSet<>();

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }
        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }
        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }
        public Builder withFavRecipes(Set<Recipe> favRecipes) {
            this.favRecipes = favRecipes;
            return this;
        }
        public UserDTO build() {
            return new UserDTO(this);
        }
    }
}
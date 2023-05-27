package hu.nye.webapp.gasztrokucko.dto;

import hu.nye.webapp.gasztrokucko.model.entity.Recipe;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

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
    }
}

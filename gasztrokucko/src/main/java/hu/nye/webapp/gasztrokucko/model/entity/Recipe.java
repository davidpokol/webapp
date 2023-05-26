package hu.nye.webapp.gasztrokucko.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.nye.webapp.gasztrokucko.model.enums.recipe.Category;
import hu.nye.webapp.gasztrokucko.model.enums.recipe.Difficulty;
import hu.nye.webapp.gasztrokucko.model.enums.recipe.ModificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "RECIPES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @SequenceGenerator(
            name = "recipe_id_sequence",
            sequenceName = "recipe_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "recipe_id_sequence"
    )
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(min = 5, message = "{validation.name.size.too_short}")
    @Column(name = "NAME", length = 60)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false)
    private User createdBy;

    @NotBlank
    @Size(min = 5, message = "{validation.name.size.too_short}")
    @Column(name = "LAST_MODIFIED", length = 50)
    private String lastModified;

    @Enumerated(EnumType.STRING)
    @NotBlank
    @Column(name = "MODIFICATION_TYPE")
    private ModificationType recipeModificationType;

    @Enumerated(EnumType.STRING)
    @NotBlank
    @Column(name = "CATEGORY")
    private Category category;

    @Enumerated(EnumType.STRING)
    @NotBlank
    @Column(name = "DIFFICULTY")
    private Difficulty difficulty;

    @ElementCollection
    @CollectionTable(name = "INGREDIENTS")
    @Column(name = "INGREDIENT", length = 30)
    private List<@NotBlank String> ingredients;

    @Lob // learn more @ https://www.baeldung.com/hibernate-lob
    @NotBlank
    @Column(name = "INSTRUCTIONS", columnDefinition = "TEXT")
    private String instructions;

    @Lob
    @Column(name = "IMAGE", columnDefinition = "BYTEA", nullable = false)
    private byte[] photo;

    @ManyToMany(mappedBy = "favRecipes", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<User> favoritedBy = new HashSet<>();
}
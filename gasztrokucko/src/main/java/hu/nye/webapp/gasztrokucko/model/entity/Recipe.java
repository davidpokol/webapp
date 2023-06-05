package hu.nye.webapp.gasztrokucko.model.entity;

import hu.nye.webapp.gasztrokucko.model.enums.recipe.Category;
import hu.nye.webapp.gasztrokucko.model.enums.recipe.Difficulty;
import hu.nye.webapp.gasztrokucko.model.enums.recipe.ModificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "RECIPES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    @Size(min = 5, message = "length too short, min 5")
    @Size(max = 60, message = "length too long, max 60")
    @Column(name = "NAME", unique = true, length = 60)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREATED_BY", nullable = false)
    private User createdBy;

    @NotBlank
    @Size(min = 5, message = "length too short, min 5")
    @Size(max = 50, message = "length too long, max 50")
    @Column(name = "LAST_MODIFIED", length = 50)
    private String lastModified;

    @Enumerated(EnumType.STRING)
    @Column(name = "MODIFICATION_TYPE")
    private ModificationType recipeModificationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "DIFFICULTY")
    private Difficulty difficulty;

    @ElementCollection
    @CollectionTable(name = "INGREDIENTS")
    @Column(name = "INGREDIENT")
    private List<@NotBlank @Size(max = 30, message = "length too long, max 30")String> ingredients;

    @Lob // learn more @ https://www.baeldung.com/hibernate-lob
    @NotBlank
    @Column(name = "INSTRUCTIONS", columnDefinition = "TEXT")
    private String instructions;
}
package hu.nye.webapp.gasztrokucko.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hu.nye.webapp.gasztrokucko.model.enums.Category;
import hu.nye.webapp.gasztrokucko.model.enums.Difficulty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false)
    private User createdBy;

    @Enumerated(EnumType.STRING)
    @NotBlank
    @Column(name = "CATEGORY")
    private Category category;

    @Enumerated(EnumType.STRING)
    @NotBlank
    @Column(name = "DIFFICULTY")
    private Difficulty difficulty;

    @ElementCollection //TODO ??
    @CollectionTable(name = "INGREDIENTS")
    @Column(name = "INGREDIENT")
    private List<@NotBlank String> ingredients;

    @Lob // learn more @ https://www.baeldung.com/hibernate-lob
    @NotBlank
    @Column(name = "INSTRUCTIONS")
    private String instructions;

    @Lob
    @Column(name = "PHOTO", columnDefinition="BLOB")
    private byte[] photo;

    @ManyToMany(mappedBy = "favRecipes", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<User> favoritedBy = new HashSet<>();
}
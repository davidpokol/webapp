package hu.nye.webapp.gasztrokucko.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {


    @Id
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "user_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    private Long id;

    @NotBlank
    @Size(min = 5, message = "length too short, min 5")
    @Size(max = 30, message = "length too long, max 30")
    @Column(name = "USERNAME", unique = true, length = 30)
    private String username;

    @NotBlank
    @Email
    @Column(name = "E_MAIL", unique = true)
    private String email;

    @NotBlank
    @Size(min = 5, message = "length too short, min 5")
    @Column(name = "PASSWORD")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "FAV_RECIPES",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "RECIPE_ID", referencedColumnName = "ID")}
    )
    private Set<Recipe> favRecipes = new HashSet<>();

}


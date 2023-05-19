package hu.nye.webapp.gasztrokucko.repository;

import hu.nye.webapp.gasztrokucko.model.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}

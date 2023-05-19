package hu.nye.webapp.gasztrokucko.repository;

import hu.nye.webapp.gasztrokucko.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

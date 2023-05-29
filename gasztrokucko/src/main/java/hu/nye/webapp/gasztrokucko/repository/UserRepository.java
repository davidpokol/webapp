package hu.nye.webapp.gasztrokucko.repository;

import hu.nye.webapp.gasztrokucko.model.dto.UserDTO;
import hu.nye.webapp.gasztrokucko.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

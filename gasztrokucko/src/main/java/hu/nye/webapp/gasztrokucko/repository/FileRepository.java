package hu.nye.webapp.gasztrokucko.repository;

import hu.nye.webapp.gasztrokucko.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}

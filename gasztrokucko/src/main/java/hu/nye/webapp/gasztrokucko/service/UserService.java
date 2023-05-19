package hu.nye.webapp.gasztrokucko.service;

import hu.nye.webapp.gasztrokucko.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> findAll();

    Optional<UserDTO> findByID(Long id);

    UserDTO create(UserDTO userDTO);

    UserDTO update(UserDTO userDTO);

    void delete(Long id);
}

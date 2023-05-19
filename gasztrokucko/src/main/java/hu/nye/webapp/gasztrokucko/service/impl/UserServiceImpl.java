package hu.nye.webapp.gasztrokucko.service.impl;

import hu.nye.webapp.gasztrokucko.dto.UserDTO;

import hu.nye.webapp.gasztrokucko.repository.UserRepository;
import hu.nye.webapp.gasztrokucko.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        return null;
    }

    @Override
    public Optional<UserDTO> findByID(Long id) {
        return Optional.empty();
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

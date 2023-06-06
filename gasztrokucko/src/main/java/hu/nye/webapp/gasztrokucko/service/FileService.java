package hu.nye.webapp.gasztrokucko.service;

import hu.nye.webapp.gasztrokucko.model.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface FileService {

    String uploadFile(Long recipeId, MultipartFile file) throws IOException;

    Optional<FileDTO> downloadFile(Long recipeId);
}

package hu.nye.webapp.gasztrokucko.service.impl;

import hu.nye.webapp.gasztrokucko.model.dto.FileDTO;
import hu.nye.webapp.gasztrokucko.model.entity.File;
import hu.nye.webapp.gasztrokucko.repository.FileRepository;
import hu.nye.webapp.gasztrokucko.service.FileService;
import hu.nye.webapp.gasztrokucko.util.FileUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    private final FileUtil fileUtil;

    private final ModelMapper modelMapper;

    public FileServiceImpl(FileUtil fileUtil, ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public String uploadFile(Long id, MultipartFile file) throws IOException {
        File fileData = fileRepository.save(File.builder()
                .id(id)
                .name(file.getOriginalFilename())
                .contentType(file.getContentType())
                .data(fileUtil.compressFile(file.getBytes())).build());
        return String.format("File uploaded successfully: %s" , file.getOriginalFilename());
    }

    @Override
    @Transactional
    public Optional<FileDTO> downloadFile(Long photoId) {

        return fileRepository.findById(photoId)
                .map(m -> modelMapper.map(m, FileDTO.class));
    }
}

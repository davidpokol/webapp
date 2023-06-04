package hu.nye.webapp.gasztrokucko.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class FileDTO {

    private Long id;

    @NotBlank
    private String name;

    private String contentType;

    private Long size;

    @Lob
    private byte[] data;

}
package hu.nye.webapp.gasztrokucko.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "FILES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class File {

    @Id
    private Long id;

    @NotBlank
    @Column(name = "NAME", length = 60)
    private String name;

    private String contentType;

    @Lob
    private byte[] data;

}
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
public class File {

    @Id
    @SequenceGenerator(
            name = "file_id_sequence",
            sequenceName = "file_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "file_id_sequence"
    )
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Column(name = "NAME", unique = true, length = 60)
    private String name;

    private String contentType;

    private Long size;

    @Lob
    private byte[] data;

}
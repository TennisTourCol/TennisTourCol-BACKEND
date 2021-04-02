package edu.escuelaing.ieti.tenistourcol.repository;
import lombok.*;
import org.springframework.data.annotation.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PlayerEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private String id;
        private String name;
        private String mail;
        private String apodo;
        private String liga;
        private String ciudad;
        private String description;
        private Integer imagen;
    }
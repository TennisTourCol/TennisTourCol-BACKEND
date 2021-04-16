package edu.escuelaing.ieti.tenistourcol.repository;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "player")
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
        private String puntos;
        private List<String> schedule;
    }

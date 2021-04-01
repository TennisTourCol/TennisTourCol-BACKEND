package edu.escuelaing.ieti.tenistourcol.repository;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.Date;

//@Builder
@NoArgsConstructor
//@AllArgsConstructor
@Document(collection = "tournament")
public @Data class TournamentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String nombre;

    private String responsable;

    private String direccion;

    private String ciudad;

    private String club;

    private String grado;

    private String categoria;

    private BigInteger precio;

    private Date hora;

    private Date fechaInicio;

    private Date fechaFin;

}

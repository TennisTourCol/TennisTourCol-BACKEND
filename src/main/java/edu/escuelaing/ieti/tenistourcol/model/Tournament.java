package edu.escuelaing.ieti.tenistourcol.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Tournament {

    private String id;

    @NotNull
    private String nombre;

    @NotNull
    private String responsable;

    @NotNull
    private String direccion;

    @NotNull
    private String ciudad;

    @NotNull
    private String club;

    @NotNull
    private String grado;

    @NotNull
    private String categoria;

    @NotNull
    private BigInteger precio;

    @NotNull
    private String hora;

    @NotNull
    private Date fechaInicio;

    @NotNull
    private Date fechaFin;


}

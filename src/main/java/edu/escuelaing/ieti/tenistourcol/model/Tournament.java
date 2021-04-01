package edu.escuelaing.ieti.tenistourcol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class Tournament implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private Date hora;

    @NotNull
    private Date fechaInicio;

    @NotNull
    private Date fechaFin;

}

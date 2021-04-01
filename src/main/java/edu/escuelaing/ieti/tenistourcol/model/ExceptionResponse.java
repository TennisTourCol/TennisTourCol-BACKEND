package edu.escuelaing.ieti.tenistourcol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class ExceptionResponse {

    private Date timestamp;
    private Integer status;
    private String error;
    private String message;
}

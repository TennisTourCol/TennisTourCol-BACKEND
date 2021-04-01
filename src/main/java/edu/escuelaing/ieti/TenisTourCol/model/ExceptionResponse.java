package edu.escuelaing.ieti.TenisTourCol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
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

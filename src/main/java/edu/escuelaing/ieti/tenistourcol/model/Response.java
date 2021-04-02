package edu.escuelaing.ieti.tenistourcol.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Response {

    private Date timestamp;
    private Integer status;
    private String message;

}

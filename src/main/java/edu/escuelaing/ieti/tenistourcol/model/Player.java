package edu.escuelaing.ieti.tenistourcol.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Player {


    private String id;

    @NotNull
    private String name, mail, apodo, liga, ciudad, description;
    private String puntos;

    private List<String> schedule;

    private Integer imagen;


}

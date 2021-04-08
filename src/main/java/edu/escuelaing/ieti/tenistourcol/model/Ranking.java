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
public class Ranking {

    private String id;

    @NotNull
    private String nombre;
    private List<Player> jugadores;
}

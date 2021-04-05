package edu.escuelaing.ieti.tenistourcol.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Match {

    private String id;

    @NotNull
    private String player1;

    @NotNull
    private String player2;

    @NotNull
    private String court;

    @NotNull
    private String round;

    private int set1P1;
    private int set2P2;
    private int set1P2;
    private int set2P1;

    private String ganador;


}

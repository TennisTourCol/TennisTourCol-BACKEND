package edu.escuelaing.ieti.tenistourcol.model;

import lombok.*;

import javax.validation.constraints.NotNull;

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

}

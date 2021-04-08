package edu.escuelaing.ieti.tenistourcol.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Notification {

    private String id;

    @NotNull
    private String playerId;

    @NotNull
    private String mailPlayer;

    @NotNull
    private String tournament;

    @NotNull
    private Date date;

}

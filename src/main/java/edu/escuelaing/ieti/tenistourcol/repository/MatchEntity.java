package edu.escuelaing.ieti.tenistourcol.repository;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Document(collection = "matches")
@Getter
@Setter
@ToString
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String player1;

    private String player2;

    private String court;

    private String round;
}

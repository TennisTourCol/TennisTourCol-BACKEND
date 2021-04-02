package edu.escuelaing.ieti.tenistourcol.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.Date;

@NoArgsConstructor
@Document(collection = "tournament")
@Getter
@Setter
@ToString
public class TournamentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;

    private String responsible;

    private String direction;

    private String city;

    private String clubSite;

    private String grade;

    private String category;

    private BigInteger price;

    private String hour;

    private Date startDate;

    private Date finalDate;
}

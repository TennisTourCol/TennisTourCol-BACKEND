package edu.escuelaing.ieti.tenistourcol.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;
import javax.persistence.Id;

@NoArgsConstructor
@Document(collection = "notifications")
@Getter
@Setter
@ToString
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String player;
    private String mailPlayer;
    private String tournament;
    private Date date;
}

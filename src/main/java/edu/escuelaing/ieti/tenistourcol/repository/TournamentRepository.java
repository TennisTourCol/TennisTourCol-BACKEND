package edu.escuelaing.ieti.tenistourcol.repository;

import edu.escuelaing.ieti.tenistourcol.model.Tournament;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TournamentRepository extends MongoRepository<Tournament, String> {

}

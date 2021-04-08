package edu.escuelaing.ieti.tenistourcol.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository extends MongoRepository<TournamentEntity, String> {
    Optional<TournamentEntity> findByName(String name);
    List<TournamentEntity> findByGrade(String grade);
}

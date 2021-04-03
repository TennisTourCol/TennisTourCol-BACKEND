package edu.escuelaing.ieti.tenistourcol.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MatchRepository extends MongoRepository<MatchEntity, String> {
    List<MatchEntity> findByRound(String round);
}

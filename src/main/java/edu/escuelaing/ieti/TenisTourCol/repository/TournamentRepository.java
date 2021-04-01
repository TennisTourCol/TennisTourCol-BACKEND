package edu.escuelaing.ieti.TenisTourCol.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TournamentRepository extends MongoRepository<TournamentEntity, String> {

}

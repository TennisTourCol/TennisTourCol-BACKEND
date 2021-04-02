package edu.escuelaing.ieti.tenistourcol.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<PlayerEntity, Long> {
}

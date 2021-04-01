package edu.escuelaing.ieti.TenisTourCol.service;

import edu.escuelaing.ieti.TenisTourCol.model.Tournament;

import java.util.List;

public interface TournamentService {

    public List<Tournament> getAll();
    public Tournament getById(String id);
    public Tournament createTournament(Tournament tournament);
}

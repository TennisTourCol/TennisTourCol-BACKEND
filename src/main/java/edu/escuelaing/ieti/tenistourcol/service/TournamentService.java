package edu.escuelaing.ieti.tenistourcol.service;

import edu.escuelaing.ieti.tenistourcol.model.Tournament;

import java.util.List;

public interface TournamentService {

    public List<Tournament> getAll();
    public Tournament getById(String id);
    public Tournament createTournament(Tournament tournament);
}

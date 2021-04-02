package edu.escuelaing.ieti.tenistourcol.service;

import edu.escuelaing.ieti.tenistourcol.model.Response;
import edu.escuelaing.ieti.tenistourcol.model.SuccessResponse;
import edu.escuelaing.ieti.tenistourcol.model.Tournament;

import java.util.List;

public interface TournamentService {

    public Response getAll();
    public Response getById(String id);
    public Response createTournament(Tournament tournament);
    public Response deleteTournament(Tournament tournament);
}

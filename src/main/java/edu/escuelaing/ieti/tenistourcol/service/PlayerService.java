package edu.escuelaing.ieti.tenistourcol.service;

import edu.escuelaing.ieti.tenistourcol.model.Player;
import edu.escuelaing.ieti.tenistourcol.model.Response;

public interface PlayerService {
    Response getUserById(String id);
    Response addTournament(String idUser, String idTournament);
    Response getSchedule(String id);
    Response createPlayer(Player player);
    Response getUserByIdAndTournament(String id, String idT);
    Response deleteTournament(String id, String idT);
    Response updatePlayerInfo(Player player);
    Response updatePuntos(Player player);
}

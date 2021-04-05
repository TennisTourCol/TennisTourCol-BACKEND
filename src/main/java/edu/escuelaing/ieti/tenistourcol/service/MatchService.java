package edu.escuelaing.ieti.tenistourcol.service;

import edu.escuelaing.ieti.tenistourcol.model.Match;
import edu.escuelaing.ieti.tenistourcol.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface MatchService {
    Response getByRound (String round);
    Response create (Match match);
    Response update(Match match);
    Response setGanador(String id, int set1P1, int set2P1, int set1P2, int set2P2);
}

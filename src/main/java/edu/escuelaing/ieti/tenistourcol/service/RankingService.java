package edu.escuelaing.ieti.tenistourcol.service;

import edu.escuelaing.ieti.tenistourcol.model.Ranking;
import edu.escuelaing.ieti.tenistourcol.model.Response;

public interface RankingService {
    Response createRanking(Ranking ranking);
    Response getRankingById(String id);
    Response getPlayersByRanking(String id);
}

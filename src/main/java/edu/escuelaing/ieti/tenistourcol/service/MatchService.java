package edu.escuelaing.ieti.tenistourcol.service;

import edu.escuelaing.ieti.tenistourcol.model.Match;
import edu.escuelaing.ieti.tenistourcol.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface MatchService {
    Response getByRound (String round);
    Response create (Match match);
}

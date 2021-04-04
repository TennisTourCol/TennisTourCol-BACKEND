package edu.escuelaing.ieti.tenistourcol.service;

import edu.escuelaing.ieti.tenistourcol.model.Response;

public interface MainView {
    Response getMatches(String date);
}

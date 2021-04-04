package edu.escuelaing.ieti.tenistourcol.controller;

import edu.escuelaing.ieti.tenistourcol.model.Match;
import edu.escuelaing.ieti.tenistourcol.model.Response;
import edu.escuelaing.ieti.tenistourcol.service.MainView;
import edu.escuelaing.ieti.tenistourcol.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/mainview")
@Validated
public class MainViewController {

    @Autowired
    MainView mainView;

    @GetMapping("/{date}")
    public ResponseEntity<Response> getMatches(@PathVariable String date){
        return ResponseEntity.ok(mainView.getMatches(date));
    }
}

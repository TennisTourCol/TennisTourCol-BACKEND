package edu.escuelaing.ieti.tenistourcol.controller;

import edu.escuelaing.ieti.tenistourcol.model.Tournament;
import edu.escuelaing.ieti.tenistourcol.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tournament")
@Validated
public class TournamentController {

    @Autowired
    TournamentService tournamentService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(tournamentService.getAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Tournament tournament) {
        return ResponseEntity.ok(tournamentService.createTournament(tournament));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        return ResponseEntity.ok(tournamentService.getById(id));
    }
}

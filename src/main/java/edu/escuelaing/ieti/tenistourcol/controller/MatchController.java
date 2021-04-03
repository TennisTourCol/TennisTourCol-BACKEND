package edu.escuelaing.ieti.tenistourcol.controller;

import edu.escuelaing.ieti.tenistourcol.model.Match;
import edu.escuelaing.ieti.tenistourcol.model.Response;
import edu.escuelaing.ieti.tenistourcol.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/match")
@Validated
public class MatchController {

    @Autowired
    MatchService matchService;

    @PostMapping
    public ResponseEntity createMatch(@Valid @RequestBody Match match){
        return ResponseEntity.ok(matchService.create(match));
    }

    @GetMapping(value = "/{round}")
    public ResponseEntity<Response> getById(@PathVariable String round){
        return ResponseEntity.ok(matchService.getByRound(round));
    }
}

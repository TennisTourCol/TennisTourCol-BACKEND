package edu.escuelaing.ieti.tenistourcol.controller;

import edu.escuelaing.ieti.tenistourcol.model.Player;
import edu.escuelaing.ieti.tenistourcol.model.Ranking;
import edu.escuelaing.ieti.tenistourcol.model.Response;
import edu.escuelaing.ieti.tenistourcol.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ranking")
@Validated
public class RankingController {

    @Autowired
    RankingService rankingService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response> getById(@PathVariable String id){
        return ResponseEntity.ok(rankingService.getRankingById(id));
    }
    @PostMapping
    public ResponseEntity<Response> create(@Valid @RequestBody Ranking ranking) {
        return ResponseEntity.ok(rankingService.createRanking(ranking));
    }
    @GetMapping(value = "/players/{id}")
    public ResponseEntity<Response> getplayersByRanking(@PathVariable String id){
        return ResponseEntity.ok(rankingService.getPlayersByRanking(id));
    }

}

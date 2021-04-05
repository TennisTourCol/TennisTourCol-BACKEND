package edu.escuelaing.ieti.tenistourcol.controller;

import edu.escuelaing.ieti.tenistourcol.model.Match;
import edu.escuelaing.ieti.tenistourcol.model.Response;
import edu.escuelaing.ieti.tenistourcol.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PutMapping
    public ResponseEntity<Response> updateMatch(@Valid @RequestBody Match match){
        return ResponseEntity.ok(matchService.update(match));
    }

    @PostMapping(value = "/{id}/resultado")
    public ResponseEntity<Response> updateMatchScore(@Valid @RequestBody List<String> resultados, @PathVariable String id){
        System.out.println("______________________________________ "+ resultados);
        System.out.println(Integer.parseInt(resultados.get(0)));

        return ResponseEntity.ok(matchService.setGanador(id, Integer.parseInt(resultados.get(0)),Integer.parseInt(resultados.get(1)),Integer.parseInt(resultados.get(2)),Integer.parseInt(resultados.get(3))));
    }


}

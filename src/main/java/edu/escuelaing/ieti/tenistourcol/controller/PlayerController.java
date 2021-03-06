package edu.escuelaing.ieti.tenistourcol.controller;

import edu.escuelaing.ieti.tenistourcol.model.Player;
import edu.escuelaing.ieti.tenistourcol.model.Response;
import edu.escuelaing.ieti.tenistourcol.model.Tournament;
import edu.escuelaing.ieti.tenistourcol.service.PlayerService;
import edu.escuelaing.ieti.tenistourcol.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/player")
@Validated
public class PlayerController {

    @Autowired
    PlayerService playerService;


    @GetMapping(value = "/{id}")
    public ResponseEntity<Response> getById(@PathVariable String id){
        return ResponseEntity.ok(playerService.getUserById(id));
    }

    @PostMapping(value = "/{id}/addTournament/{idT}")
    public ResponseEntity<Response> addTournament(@PathVariable String id, @PathVariable String idT){
        return ResponseEntity.ok(playerService.addTournament(id,idT));
    }

    @GetMapping(value = "/{id}/schedule")
    public ResponseEntity<Response> getSchedule(@PathVariable String id){
        return ResponseEntity.ok(playerService.getSchedule(id));
    }
    @PostMapping
    public ResponseEntity<Response> create(@Valid @RequestBody Player player) {
        return ResponseEntity.ok(playerService.createPlayer(player));
    }

    @GetMapping(value = "/{id}/tournament/{idT}")
    public ResponseEntity<Response> getByIdAndTournament(@PathVariable String id, @PathVariable String idT){
        return ResponseEntity.ok(playerService.getUserByIdAndTournament(id, idT));
    }

    @DeleteMapping(value = "/{id}/tournament/{idT}")
    public ResponseEntity<Response> delete(@PathVariable String id, @PathVariable String idT) {
        return ResponseEntity.ok(playerService.deleteTournament(id, idT));
    }
    @PostMapping(value = "/updatePlayer")
    public ResponseEntity<Response> updateInfoPlayer(@Valid @RequestBody Player player) {
        return ResponseEntity.ok(playerService.updatePlayerInfo(player));
    }
    @PostMapping(value = "/updatePuntos")
    public ResponseEntity<Response> updatePuntos(@Valid @RequestBody Player player) {
        return ResponseEntity.ok(playerService.updatePuntos(player));
    }


}

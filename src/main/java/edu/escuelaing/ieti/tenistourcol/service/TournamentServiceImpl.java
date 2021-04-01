package edu.escuelaing.ieti.tenistourcol.service;

import edu.escuelaing.ieti.tenistourcol.model.Tournament;
import edu.escuelaing.ieti.tenistourcol.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentServiceImpl implements TournamentService{

    @Autowired
    private TournamentRepository tournamentRepository;

    @Override
    public List<Tournament> getAll() {
        List<Tournament> tournaments = new ArrayList<>();
        List<Tournament> tournamentEntities = tournamentRepository.findAll();
        tournamentEntities.forEach(tournament -> tournaments.add(tournament));
        return tournaments;
    }

    @Override
    public Tournament getById(String id) {
        Optional<Tournament> optTournament = tournamentRepository.findById(id);
        if(optTournament.isPresent()) {
            return optTournament.get();
        } else {
            return null;
        }
    }

    @Override
    public Tournament createTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
        return tournament;
    }
}

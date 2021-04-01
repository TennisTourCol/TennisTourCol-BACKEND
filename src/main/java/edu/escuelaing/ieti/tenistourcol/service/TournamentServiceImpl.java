package edu.escuelaing.ieti.tenistourcol.service;

import edu.escuelaing.ieti.tenistourcol.mapper.TournamentMapper;
import edu.escuelaing.ieti.tenistourcol.model.Tournament;
import edu.escuelaing.ieti.tenistourcol.repository.TournamentEntity;
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
        List<TournamentEntity> tournamentEntities = tournamentRepository.findAll();
        tournamentEntities.forEach(tournamentEntity -> tournaments.add(TournamentMapper.map(tournamentEntity)));
        return tournaments;
    }

    @Override
    public Tournament getById(String id) {
        Optional<TournamentEntity> optTournament = tournamentRepository.findById(id);
        if(optTournament.isPresent()) {
            return TournamentMapper.map(optTournament.get());
        } else {
            return null;
        }
    }

    @Override
    public Tournament createTournament(Tournament tournament) {
        TournamentEntity tournamentEntity = TournamentMapper.map(tournament);
        tournamentRepository.save(tournamentEntity);
        return TournamentMapper.map(tournamentEntity);
    }
}

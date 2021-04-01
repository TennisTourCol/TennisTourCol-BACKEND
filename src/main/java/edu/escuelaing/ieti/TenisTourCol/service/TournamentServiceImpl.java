package edu.escuelaing.ieti.TenisTourCol.service;

import edu.escuelaing.ieti.TenisTourCol.mapper.TournamentMapper;
import edu.escuelaing.ieti.TenisTourCol.model.Tournament;
import edu.escuelaing.ieti.TenisTourCol.repository.TournamentEntity;
import edu.escuelaing.ieti.TenisTourCol.repository.TournamentRepository;
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
        try {
            List<Tournament> tournaments = new ArrayList<>();
            List<TournamentEntity> tournamentEntities = tournamentRepository.findAll();
            tournamentEntities.forEach(tournamentEntity -> tournaments.add(TournamentMapper.map(tournamentEntity)));
            return tournaments;
        } catch (Exception e) {
            throw e;
            //throw new ExceptionResponse(new Date(), 400, "Bad Request", e.getMessage());
            /*System.out.println("Exception Saving: "+e.getMessage());
            return null;*/
        }
    }

    @Override
    public Tournament getById(String id) {
        try {
            Optional<TournamentEntity> optTournament = tournamentRepository.findById(id);
            if(optTournament.isPresent()) {
                return TournamentMapper.map(optTournament.get());
            } else {
                return null;
            }
        } catch (Exception e) {
            /*System.out.println("Exception Saving: "+e.getMessage());
            return null;*/
            throw e;
        }
    }

    @Override
    public Tournament createTournament(Tournament tournament) {
        try {
            TournamentEntity tournamentEntity = TournamentMapper.map(tournament);
            tournamentRepository.save(tournamentEntity);
            return TournamentMapper.map(tournamentEntity);
        } catch (Exception e) {
            /*System.out.println("Exception Saving: "+e.getMessage());
            return null;*/
            throw e;
        }
    }
}

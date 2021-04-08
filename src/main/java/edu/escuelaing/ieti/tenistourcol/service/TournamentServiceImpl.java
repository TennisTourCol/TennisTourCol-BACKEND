package edu.escuelaing.ieti.tenistourcol.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.escuelaing.ieti.tenistourcol.exception.NotFoundException;
import edu.escuelaing.ieti.tenistourcol.mapper.TournamentMapper;
import edu.escuelaing.ieti.tenistourcol.model.Response;
import edu.escuelaing.ieti.tenistourcol.model.SuccessResponse;
import edu.escuelaing.ieti.tenistourcol.model.Tournament;
import edu.escuelaing.ieti.tenistourcol.repository.TournamentEntity;
import edu.escuelaing.ieti.tenistourcol.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentServiceImpl implements TournamentService{

    @Autowired
    private TournamentRepository tournamentRepository;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    @Override
    public SuccessResponse getAll() {
        List<Tournament> tournaments = new ArrayList<>();
        List<TournamentEntity> tournamentEntities = tournamentRepository.findAll();
        tournamentEntities.forEach(tournamentEntity -> tournaments.add(TournamentMapper.map(tournamentEntity)));
        if(tournaments.size()>0) {
            return new SuccessResponse(new Date(), 200, "Se encontraron los torneos", gson.toJson(tournaments));
        } else {
            throw new NotFoundException("No se encontró ningún torneo");
        }
    }

    @Override
    public SuccessResponse getById(String id) {
        Optional<TournamentEntity> optTournament = tournamentRepository.findById(id);
        if(optTournament.isPresent()) {
            return new SuccessResponse(new Date(), 200, "Se encontraron los torneos", gson.toJson(TournamentMapper.map(optTournament.get())));
        } else {
            throw new NotFoundException("No se encontró ningún torneo con el id "+id);
        }
    }

    @Override
    public SuccessResponse createTournament(Tournament tournament) {
        TournamentEntity tournamentEntity = TournamentMapper.map(tournament);
        tournamentRepository.save(tournamentEntity);
        return new SuccessResponse(new Date(), 200, "Se creo el torneo "+tournament.getNombre(), gson.toJson(TournamentMapper.map(tournamentEntity)));
    }

    @Override
    public Response deleteTournament(Tournament tournament) {
        tournamentRepository.delete(TournamentMapper.map(tournament));
        return new SuccessResponse(new Date(), 200, "Se elimino el torneo "+tournament.getNombre(), gson.toJson(TournamentMapper.map(tournament)));
    }
    @Override
    public Response editTournament(Tournament tournament) {
        Optional<TournamentEntity> tourOpt= tournamentRepository.findById(tournament.getId());
        if(tourOpt.isPresent()){
            TournamentEntity tournamentEntity= tourOpt.get();
                tournamentEntity.setName(tournament.getNombre());
                tournamentEntity.setGrade(tournament.getGrado());
                tournamentEntity.setDirection(tournament.getDireccion());
                tournamentEntity.setClubSite(tournament.getClub());
                tournamentEntity.setCity(tournament.getCiudad());
                tournamentEntity.setStartDate(tournament.getFechaInicio());
                tournamentEntity.setFinalDate(tournament.getFechaFin());
                tournamentEntity.setHour(tournament.getHora());
            tournamentRepository.save(tournamentEntity);
            return new SuccessResponse(new Date(), 200, "Se actualizo torneo " , gson.toJson(tournament));
        }else{
            throw new NotFoundException("No se encontro el torneo con id "+tournament.getId());
        }
    }
    @Override
    public Response getByName(String name) {
        Optional<TournamentEntity> optTournament = tournamentRepository.findByName(name);
        if(optTournament.isPresent()) {
            return new SuccessResponse(new Date(), 200, "Se encontraron los torneos", gson.toJson(TournamentMapper.map(optTournament.get())));
        } else {
            throw new NotFoundException("No se encontró ningún torneo con el nombre "+name);
        }
    }

    @Override
    public Response getByGrade(String grade) {
        List<Tournament> tournaments = new ArrayList<>();
        List<TournamentEntity> tournamentEntities = tournamentRepository.findByGrade(grade);
        tournamentEntities.forEach(tournamentEntity -> tournaments.add(TournamentMapper.map(tournamentEntity)));
        if(tournaments.size()>0) {
            return new SuccessResponse(new Date(), 200, "Se encontraron los torneos", gson.toJson(tournaments));
        } else {
            throw new NotFoundException("No se encontró ningún torneo con grado " + grade);
        }
    }
}

package edu.escuelaing.ieti.tenistourcol.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.escuelaing.ieti.tenistourcol.exception.NotFoundException;
import edu.escuelaing.ieti.tenistourcol.mapper.MatchMapper;
import edu.escuelaing.ieti.tenistourcol.model.Match;
import edu.escuelaing.ieti.tenistourcol.model.Response;
import edu.escuelaing.ieti.tenistourcol.model.SuccessResponse;
import edu.escuelaing.ieti.tenistourcol.repository.MatchEntity;
import edu.escuelaing.ieti.tenistourcol.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class MatchServiceImpl implements MatchService{

    @Autowired
    MatchRepository matchRepository;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    @Override
    public Response getByRound(String round) {
        List<Match> matches = new ArrayList<>();
        List<MatchEntity> matchEntities = matchRepository.findByRound(round);
        matchEntities.forEach(matchEntity -> matches.add(MatchMapper.map(matchEntity)));
        if(matches.size()>0) {
            return new SuccessResponse(new Date(), 200, "Se encontraron los partidos de la ronda " + round, gson.toJson(matches));
        } else {
            throw new NotFoundException("No se encontró ningún partido de la ronda " + round);
        }
    }

    @Override
    public Response create(Match match) {
        MatchEntity matchEntity = MatchMapper.map(match);
        matchRepository.save(matchEntity);
        return new SuccessResponse(new Date(), 200, "Se creo el partido " , gson.toJson(MatchMapper.map(matchEntity)));
    }

    @Override
    public Response update(Match match) {
        Optional<MatchEntity> matchOpt = matchRepository.findById(match.getId());
        if(matchOpt.isPresent()){
            MatchEntity matchEntity = matchOpt.get();
            matchEntity.setPlayer1(match.getPlayer1());
            matchEntity.setPlayer2(match.getPlayer2());
            matchEntity.setRound(match.getRound());
            matchRepository.save(matchEntity);
            return new SuccessResponse(new Date(), 200, "Se actualizo el partido " , gson.toJson(match));
        } else {
            throw new NotFoundException("No se encontro el partido con id "+match.getId());
        }
    }
}

package edu.escuelaing.ieti.tenistourcol.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import edu.escuelaing.ieti.tenistourcol.exception.NotFoundException;
import edu.escuelaing.ieti.tenistourcol.mapper.PlayerMapper;
import edu.escuelaing.ieti.tenistourcol.mapper.TournamentMapper;
import edu.escuelaing.ieti.tenistourcol.model.Player;
import edu.escuelaing.ieti.tenistourcol.model.Response;
import edu.escuelaing.ieti.tenistourcol.model.SuccessResponse;
import edu.escuelaing.ieti.tenistourcol.repository.PlayerEntity;
import edu.escuelaing.ieti.tenistourcol.repository.PlayerRepository;
import edu.escuelaing.ieti.tenistourcol.repository.TournamentEntity;
import edu.escuelaing.ieti.tenistourcol.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class PlayerServiceImpl implements PlayerService{
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    @Override
    public Response getUserById(String id) {
        Optional<PlayerEntity> optPlayer = playerRepository.findById(id);
        if(optPlayer.isPresent()) {
            return new SuccessResponse(new Date(), 200, "Se encontro el jugador", gson.toJson(PlayerMapper.map(optPlayer.get())));
        } else {
            throw new NotFoundException("No se encontró ningún jugador con el id "+id);
        }
    }

    @Override
    public Response addTournament(String idUser, String idTournament) {
        Optional<PlayerEntity> optPlayer = playerRepository.findById(idUser);
        Optional<TournamentEntity> optTournament = tournamentRepository.findById(idTournament);
        if(optPlayer.isPresent() && optTournament.isPresent()) {
            List<String> schedule = optPlayer.get().getSchedule();
            schedule.add(idTournament);
            optPlayer.get().setSchedule(schedule);
            Player temp = PlayerMapper.map(optPlayer.get());
            PlayerEntity temp2 = PlayerMapper.map(temp);
            playerRepository.save(temp2);

            return new SuccessResponse(new Date(), 200, "Se añadio el torneo de manera correcta", gson.toJson(PlayerMapper.map(optPlayer.get())));
        } else {
            throw new NotFoundException("No se encontró ningún jugador con el id "+idUser+" o ningun torneo con el id "+ idTournament);
        }

    }

    @Override
    public Response getSchedule(String id) {
        //Optional<PlayerEntity> optPlayer = playerRepository.findById(id);
        //if(optPlayer.isPresent()) {
        //    return new SuccessResponse(new Date(), 200, "Se encontro el jugador", gson.toJson(optPlayer.get().getSchedule()));
        //} else {
        //    throw new NotFoundException("No se encontró ningún jugador con el id "+id);
        //}
        return null;
    }

    @Override
    public Response createPlayer(Player player) {
        PlayerEntity playerEntity = PlayerMapper.map(player);
        playerRepository.save(playerEntity);
        return new SuccessResponse(new Date(), 200, "Se creo el torneo "+player.getName(), gson.toJson(PlayerMapper.map(playerEntity)));
    }

}

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
import java.util.Optional;
@Service
public class PlayerServiceImpl implements PlayerService{
    @Autowired
    private PlayerRepository playerRepository;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    @Override
    public Response getUserById(Long id) {
        Optional<PlayerEntity> optPlayer = playerRepository.findById(id);
        if(optPlayer.isPresent()) {
            return new SuccessResponse(new Date(), 200, "Se encontro el jugador", gson.toJson(PlayerMapper.map(optPlayer.get())));
        } else {
            throw new NotFoundException("No se encontró ningún jugador con el id "+id);
        }
    }

}

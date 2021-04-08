package edu.escuelaing.ieti.tenistourcol.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.escuelaing.ieti.tenistourcol.exception.NotFoundException;
import edu.escuelaing.ieti.tenistourcol.mapper.PlayerMapper;
import edu.escuelaing.ieti.tenistourcol.mapper.RankingMapper;
import edu.escuelaing.ieti.tenistourcol.model.*;
import edu.escuelaing.ieti.tenistourcol.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RankingServiceImpl implements RankingService{
    @Autowired
    private RankingRepository rankingRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    @Override
    public Response createRanking(Ranking ranking) {
        RankingEntity rankingEntity = RankingMapper.map(ranking);
        rankingRepository.save(rankingEntity);
        return new SuccessResponse(new Date(), 200, "Se creo el ranking "+ranking.getNombre(), gson.toJson(RankingMapper.map(rankingEntity)));
    }

    @Override
    public Response getRankingById(String id) {
        Optional<RankingEntity> tmp= rankingRepository.findById(id);
        if(tmp.isPresent()){
            return new SuccessResponse(new Date(), 200, "Se encontró el ranking", gson.toJson(RankingMapper.map(tmp.get())));
        } else {
            throw new NotFoundException("No se encontró ningún ranking con el id "+id);
        }

    }

    @Override
    public Response getPlayersByRanking(String id) {
        Optional<RankingEntity> tmp = rankingRepository.findById(id);
        if(tmp.isPresent()){
            List<Player> jugadores = new ArrayList<>();
            for(Player p: tmp.get().getJugadores()){
                Optional<PlayerEntity> optPlayer = playerRepository.findById(p.getId());
                if(optPlayer.isPresent()){
                    Player temp = PlayerMapper.map(optPlayer.get());
                    jugadores.add(temp);
                }
                else{
                    throw new NotFoundException("No se encontró ningún jugador con el id "+p.getId()+" dentro de la busqueda del ranking");
                }
            }return new SuccessResponse(new Date(), 200, "Se encontraron los jugadores", gson.toJson(jugadores));
        } else {
            throw new NotFoundException("No se encontró ningún ranking con el id "+id);
        }
    }
}

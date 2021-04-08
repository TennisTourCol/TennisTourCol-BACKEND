package edu.escuelaing.ieti.tenistourcol.mapper;

import edu.escuelaing.ieti.tenistourcol.model.Ranking;
import edu.escuelaing.ieti.tenistourcol.repository.RankingEntity;

public class RankingMapper {

    private RankingMapper(){
    }

    public static RankingEntity map(final Ranking ranking){


        final RankingEntity rankingEntity = new RankingEntity();

        rankingEntity.setId(ranking.getId());
        rankingEntity.setName(ranking.getNombre());
        rankingEntity.setJugadores(ranking.getJugadores());

        return rankingEntity;
    }

    public static Ranking map(final RankingEntity rankingEntity){
        Ranking ranking = new Ranking();
        ranking.setId(rankingEntity.getId());
        ranking.setNombre(rankingEntity.getName());
        ranking.setJugadores(rankingEntity.getJugadores());

        return ranking;
    }
}

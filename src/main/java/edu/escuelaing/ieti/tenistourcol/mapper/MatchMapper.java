package edu.escuelaing.ieti.tenistourcol.mapper;

import edu.escuelaing.ieti.tenistourcol.model.Match;
import edu.escuelaing.ieti.tenistourcol.repository.MatchEntity;

public class MatchMapper {

    private MatchMapper() {
    }

    public static MatchEntity map(final Match match) {

        final MatchEntity matchEntity = new MatchEntity();

        matchEntity.setId(match.getId());
        matchEntity.setPlayer1(match.getPlayer1());
        matchEntity.setPlayer2(match.getPlayer2());
        matchEntity.setCourt(match.getCourt());
        matchEntity.setRound(match.getRound());

        return matchEntity;
    }

    public static Match map(final MatchEntity matchEntity) {

        Match match = new Match();

        match.setId(matchEntity.getId());
        match.setPlayer1(matchEntity.getPlayer1());
        match.setPlayer2(matchEntity.getPlayer2());
        match.setCourt(matchEntity.getCourt());
        match.setRound(matchEntity.getRound());

        return match;
    }
}

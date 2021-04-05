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
        matchEntity.setSet1P1(match.getSet1P1());
        matchEntity.setSet1P2(match.getSet1P2());
        matchEntity.setSet2P1(match.getSet2P1());
        matchEntity.setSet2P2(match.getSet2P2());
        matchEntity.setGanador(match.getGanador());
        return matchEntity;
    }

    public static Match map(final MatchEntity matchEntity) {

        Match match = new Match();

        match.setId(matchEntity.getId());
        match.setPlayer1(matchEntity.getPlayer1());
        match.setPlayer2(matchEntity.getPlayer2());
        match.setCourt(matchEntity.getCourt());
        match.setRound(matchEntity.getRound());
        match.setSet1P1(matchEntity.getSet1P1());
        match.setSet1P2(matchEntity.getSet1P2());
        match.setSet2P1(matchEntity.getSet2P1());
        match.setSet2P2(matchEntity.getSet2P2());
        match.setGanador(matchEntity.getGanador());
        return match;
    }
}

package edu.escuelaing.ieti.tenistourcol.mapper;

import edu.escuelaing.ieti.tenistourcol.model.Tournament;
import edu.escuelaing.ieti.tenistourcol.repository.TournamentEntity;

public class TournamentMapper {

    private TournamentMapper() {
    }

    public static TournamentEntity map(final Tournament tournament) {

        final TournamentEntity tournamentEntity = new TournamentEntity();

        tournamentEntity.setId(tournament.getId());
        tournamentEntity.setName(tournament.getNombre());
        tournamentEntity.setResponsible(tournament.getResponsable());
        tournamentEntity.setDirection(tournament.getDireccion());
        tournamentEntity.setCity(tournament.getCiudad());
        tournamentEntity.setClubSite(tournament.getClub());
        tournamentEntity.setGrade(tournament.getGrado());
        tournamentEntity.setCategory(tournament.getCategoria());
        tournamentEntity.setPrice(tournament.getPrecio());
        tournamentEntity.setHour(tournament.getHora());
        tournamentEntity.setStartDate(tournament.getFechaInicio());
        tournamentEntity.setFinalDate(tournament.getFechaFin());

        return tournamentEntity;
    }

    public static Tournament map(final TournamentEntity tournamentEntity) {

        Tournament tournament = new Tournament();

        tournament.setId(tournamentEntity.getId());
        tournament.setNombre(tournamentEntity.getName());
        tournament.setResponsable(tournamentEntity.getResponsible());
        tournament.setDireccion(tournamentEntity.getDirection());
        tournament.setCiudad(tournamentEntity.getCity());
        tournament.setClub(tournamentEntity.getClubSite());
        tournament.setGrado(tournamentEntity.getGrade());
        tournament.setCategoria(tournamentEntity.getCategory());
        tournament.setPrecio(tournamentEntity.getPrice());
        tournament.setHora(tournamentEntity.getHour());
        tournament.setFechaInicio(tournamentEntity.getStartDate());
        tournament.setFechaFin(tournamentEntity.getFinalDate());

        return tournament;
    }

}

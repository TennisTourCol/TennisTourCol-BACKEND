package edu.escuelaing.ieti.tenistourcol.mapper;

import edu.escuelaing.ieti.tenistourcol.model.Tournament;
import edu.escuelaing.ieti.tenistourcol.repository.TournamentEntity;

public class TournamentMapper {

    public static TournamentEntity map(final Tournament tournament) {

        final TournamentEntity tournamentEntity = new TournamentEntity();

        tournamentEntity.setId(tournament.getId());
        tournamentEntity.setNombre(tournament.getNombre());
        tournamentEntity.setResponsable(tournament.getResponsable());
        tournamentEntity.setDireccion(tournament.getDireccion());
        tournamentEntity.setCiudad(tournament.getCiudad());
        tournamentEntity.setClub(tournament.getClub());
        tournamentEntity.setGrado(tournament.getGrado());
        tournamentEntity.setCategoria(tournament.getCategoria());
        tournamentEntity.setPrecio(tournament.getPrecio());
        tournamentEntity.setHora(tournament.getHora());
        tournamentEntity.setFechaInicio(tournament.getFechaInicio());
        tournamentEntity.setFechaFin(tournament.getFechaFin());

        return tournamentEntity;
    }

    public static Tournament map(final TournamentEntity tournamentEntity) {

        Tournament tournament = new Tournament();

        tournament.setId(tournamentEntity.getId());
        tournament.setId(tournamentEntity.getId());
        tournament.setNombre(tournamentEntity.getNombre());
        tournament.setResponsable(tournamentEntity.getResponsable());
        tournament.setDireccion(tournamentEntity.getDireccion());
        tournament.setCiudad(tournamentEntity.getCiudad());
        tournament.setClub(tournamentEntity.getClub());
        tournament.setGrado(tournamentEntity.getGrado());
        tournament.setCategoria(tournamentEntity.getCategoria());
        tournament.setPrecio(tournamentEntity.getPrecio());
        tournament.setHora(tournamentEntity.getHora());
        tournament.setFechaInicio(tournamentEntity.getFechaInicio());
        tournament.setFechaFin(tournamentEntity.getFechaFin());

        return tournament;
    }

}
